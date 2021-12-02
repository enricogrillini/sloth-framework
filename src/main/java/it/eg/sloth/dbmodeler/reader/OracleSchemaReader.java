package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.code.Argument;
import it.eg.sloth.dbmodeler.model.schema.code.ArgumentType;
import it.eg.sloth.dbmodeler.model.schema.code.StoredProcedure;
import it.eg.sloth.dbmodeler.model.schema.table.Constraint;
import it.eg.sloth.dbmodeler.model.schema.table.ConstraintType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;

public class OracleSchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String GEN_GENERATED_NAME = "GENERATED NAME";

    private static final String SQL_TABLES = "Select lower(t.table_name) table_name,\n" +
            "       c.comments table_comments,\n" +
            "       t.tablespace_name,\n" +
            "       t.initial_extent,\n" +
            "       t.Temporary temporary_table,\n" +
            "       t.Duration,\n" +
            "       tc.column_name,\n" +
            "       cc.comments column_comments,\n" +
            "       tc.nullable,\n" +
            "       tc.data_type,\n" +
            "       tc.data_length,\n" +
            "       tc.data_precision,\n" +
            "       tc.data_scale,\n" +
            "       tc.data_default\n" +
            "From all_tables t\n" +
            "     inner join all_tab_columns tc on (t.owner = tc.owner And t.table_name = tc.table_name)\n" +
            "     left join all_tab_comments c on  (t.owner = c.owner And t.table_name = c.Table_name)\n" +
            "     left join all_col_comments cc on  (tc.owner = cc.owner And tc.table_name = cc.table_name And tc.column_name = cc.column_name ANd c.TABLE_TYPE = 'TABLE')\n" +
            "Where t.NESTED = 'NO' And\n" +
            // Filtri
            "      t.owner = upper(?) And\n" +
            // Escludo dalle tabelle di sistema per import/export
            "      t.table_name not like 'SYS_EXPORT_SCHEMA%' And\n" +
            "      t.table_name not like 'SYS_IMPORT_FULL%' And\n" +

            // Escludo dalle tabelle le viste materializzate:
            "      Not Exists (Select 1 From ALL_mviews mv Where mv.owner = t.owner And mv.mview_name = t.table_name)\n" +
            "/*W*/\n" +
            "Order by t.table_Name, tc.column_id";

    private static final String SQL_DB_CONSTRAINT = "Select c1.table_name,\n" +
            "       decode (c1.constraint_type, 'P', 1, 'R', 2, 'C', 3) Ordinamento,\n" +
            "       c1.constraint_name,\n" +
            "       cc1.position,\n" +
            "       cc1.column_name,\n" +
            "       c1.constraint_type,\n" +
            "       c1.search_condition,\n" +
            "       c1.generated,\n" +
            "       null references_table\n" +
            "From all_cons_columns cc1, all_constraints c1\n" +
            "Where c1.owner = cc1.owner And\n" +
            "      c1.constraint_name = cc1.constraint_name And\n" +
            "      c1.owner = upper(?)  And\n" +
            "      c1.constraint_type in ('P', 'U', 'C')\n" +
            "Union All\n" +
            "Select c1.table_name,\n" +
            "       decode (c1.constraint_type, 'P', 1, 'R', 2, 'C', 3) Ordinamento,\n" +
            "       c1.constraint_name,\n" +
            "       cc1.position,\n" +
            "       cc1.column_name,\n" +
            "       c1.constraint_type,\n" +
            "       c1.search_condition,\n" +
            "       c1.generated,\n" +
            "       c2.table_name references_table\n" +
            "From all_cons_columns cc1, all_constraints c2, all_constraints c1\n" +
            "Where c1.owner = cc1.owner And\n" +
            "      c1.constraint_name = cc1.constraint_name And\n" +
            "      c1.owner = upper(?) And\n" +
            "      c1.constraint_type in ('R') And\n" +
            "      c1.r_owner = c2.owner And\n" +
            "      c1.r_constraint_name = c2.constraint_name\n" +
            "Order By 1, 2, 3, 4\n";

    private static final String SQL_DB_INDEXES = "Select i.index_name,\n" +
            "       ic.column_position,\n" +
            "       i.owner,\n" +
            "       i.table_name,\n" +
            "       i.uniqueness,\n" +
            "       i.tablespace_name,\n" +
            "       i.initial_extent,\n" +
            "       ic.column_name\n" +
            "From all_ind_columns ic, all_indexes i\n" +
            "Where i.OWNER = ic.index_owner And\n" +
            "      i.INDEX_NAME = ic.index_name And\n" +
            "      i.table_owner = upper(?) And\n" +
            "      i.index_type like '%NORMAL%'\n" +
            "minus\n" +
            "Select i.index_name,\n" +
            "       ic.column_position,\n" +
            "       i.owner,\n" +
            "       i.table_name,\n" +
            "       i.uniqueness,\n" +
            "       i.tablespace_name,\n" +
            "       i.initial_extent,\n" +
            "       ic.column_name\n" +
            "From all_mviews mv, all_ind_columns ic, all_indexes i\n" +
            "Where mv.owner = i.table_owner And\n" +
            "      mv.mview_name = i.table_name And\n" +
            "      i.OWNER = ic.index_owner And\n" +
            "      i.INDEX_NAME = ic.index_name And\n" +
            "      i.table_owner = upper(?) And\n" +
            "      i.index_type like '%NORMAL%'\n" +
            "Order by 1, 2\n";

    private static final String SQL_CONSTANTS = "select * from {0} Order by NLSSORT({1},''NLS_SORT=BINARY'')";

    private static final String SQL_DB_SEQUENCES = "Select sequence_name\n" +
            "From all_sequences\n" +
            "Where sequence_owner = upper(?)\n" +
            "Order By sequence_name";

    private static final String SQL_VIEWS = "Select InitCap(t.view_name) view_name,\n" +
            "       t.text As definition,\n" +
            "       c.comments view_comments\n" +
            "From all_tab_comments c\n" +
            "     inner join all_Views t on t.owner = c.owner And t.view_name = c.Table_name\n" +
            "Where t.owner = upper(?) And\n" +
            "      c.TABLE_TYPE = 'VIEW'\n" +
            "Order by t.view_name";

    private static final String SQL_VIEWS_COLUMNS = "Select InitCap(t.view_name) view_name,\n" +
            "       InitCap(tc.column_name) column_name,\n" +
            "       cc.comments column_comments,\n" +
            "       tc.nullable,\n" +
            "       tc.data_type,\n" +
            "       tc.data_length,\n" +
            "       tc.data_precision,\n" +
            "       tc.data_scale\n" +
            "From All_Views t\n" +
            "     Inner Join all_tab_columns tc on t.owner = tc.owner And t.view_name = tc.table_name\n" +
            "     Inner Join all_col_comments cc on tc.owner = cc.owner And tc.table_name = cc.table_name And tc.column_name = cc.column_name\n" +
            "Where t.owner = upper(?) \n" +
            "Order by t.view_name, tc.column_id\n";

    private static final String SQL_SOURCE = "Select lower(NAME) name, TYPE, LINE, TEXT\n" +
            "From all_source\n" +
            "Where owner = upper(?)\n" +
            "Order by type, name, line\n";


    private static final String SQL_PROCEDURES_ARGUMENTS = "Select InitCap(a.object_name) object_name,\n" +
            "       InitCap(a.package_name) package_name,\n" +
            "       nvl(a.overload, 0) overload,\n" +
            "       InitCap(a.argument_name) argument_name,\n" +
            "       a.position,\n" +
            "       a.data_type,\n" +
            "       a.in_out,\n" +
            "       o.object_type,\n" +
            "       a.type_name\n" +
            "From ALL_arguments a, all_objects o\n" +
            "Where a.owner = o.owner And\n" +
            "      a.object_name = o.object_name And\n" +
            "      a.owner = upper(?) And\n" +
            "      a.package_name is null And\n" +
            "      a.data_level = 0\n" +
            "Order By package_name, object_name, overload, sequence";

    private static final String SQL_PACKAGES = "Select InitCap(a.object_name) object_name,\n" +
            "       InitCap(a.package_name) package_name,\n" +
            "       nvl(a.overload, 0) overload,\n" +
            "       InitCap(a.argument_name) argument_name,\n" +
            "       a.position,\n" +
            "       a.data_type,\n" +
            "       a.in_out,\n" +
            "       o.object_type," +
            "       a.type_name\n" +
            "From ALL_arguments a, All_objects o\n" +
            "Where a.owner = o.owner And\n" +
            "      a.package_name = o.object_name And\n" +
            "      a.owner = upper(?) And\n" +
            "      a.package_name is not null And\n" +
            "      a.data_level = 0 And\n" +
            "      o.object_type = 'PACKAGE'\n" +
            "Order By package_name, object_name, overload, sequence";

    private static final String SQL_STATS = "select Sum(decode(s.segment_type, 'TABLE', 1, 0)) Table_Count,\n" +
            "       Sum(decode(s.segment_type, 'TABLE', s.bytes, 0)) Table_Size,\n" +
            "       Sum(decode(s.segment_type, 'INDEX', 1, 0)) Index_Count,\n" +
            "       Sum(decode(s.segment_type, 'INDEX', s.bytes, 0)) Index_Size,\n" +
            "       Sum(decode(r.object_name, null, 0, 1)) RecycleBin_Count,\n" +
            "       Sum(decode(r.object_name, null, 0, bytes)) RecycleBin_Size\n" +
            "from dba_segments s\n" +
            "     left join dba_recyclebin r on s.segment_name = r.object_name And s.owner = r.owner\n" +
            "Where s.owner = upper(?)\n";


    public OracleSchemaReader(DataBaseType dataBaseType) {
        super(dataBaseType);
    }

    @Override
    public <R extends DataRow> DataTable<R> tablesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_TABLES);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> constraintsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_CONSTRAINT);
        query.addParameter(Types.VARCHAR, owner);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> indexesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_INDEXES);
        query.addParameter(Types.VARCHAR, owner);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> constantsData(Connection connection, String tableName, String keyName) throws FrameworkException, SQLException, IOException {
        Query query = new Query(MessageFormat.format(SQL_CONSTANTS, tableName, keyName));

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> sequencesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_SEQUENCES);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> viewsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_VIEWS);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> viewsColumnsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_VIEWS_COLUMNS);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> storedProcedureData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_SOURCE);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    public <R extends DataRow> DataTable<R> sourcesArguments(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_PROCEDURES_ARGUMENTS);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> statisticsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_STATS);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public String calcColumnType(DataRow dataRow) {
        String dataType = dataRow.getString("data_type").trim();
        BigDecimal dataPrecision = BaseFunction.nvl(dataRow.getBigDecimal("data_precision"), new BigDecimal(0));
        BigDecimal dataScale = BaseFunction.nvl(dataRow.getBigDecimal("data_scale"), new BigDecimal(0));
        BigDecimal dataLength = BaseFunction.nvl(dataRow.getBigDecimal("data_length"), new BigDecimal(0));

        String result = dataType;
        // "DATE", "LONG", "LONG RAW", "BLOB", "CLOB"
        if ("NUMBER".equals(dataType) && dataPrecision.intValue() == 0) {
            result += "(38," + dataScale + ")";
        } else if ("NUMBER".equals(dataType) && dataPrecision.intValue() != 0) {
            result += "(" + dataPrecision.intValue() + "," + dataScale.intValue() + ")";
        } else if ("FLOAT".equals(dataType)) {
            result += "(" + dataPrecision.intValue() + ")";
        } else if (dataType.indexOf("TIMESTAMP") >= 0) {
            result += "";
        } else if ("DATE".equals(dataType) || "CLOB".equals(dataType) || "BLOB".equals(dataType)) {
            result += "";
        } else {
            result += "(" + dataLength.intValue() + ")";
        }

        return result;
    }

    private ConstraintType calcConstraintType(String type) {
        if ("P".equals(type)) {
            return ConstraintType.PRIMARY_KEY;
        } else if ("R".equals(type)) {
            return ConstraintType.FOREIGN_KEY;
        } else {
            return ConstraintType.CHECK;
        }
    }

    public void addConstraints(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException {
        // Constraints Data
        DataTable<?> dataConstraintsTable = constraintsData(connection, owner);

        // Elaborazione
        for (DataRow dataRow : dataConstraintsTable) {
            // Rottura su constraint
            String constraintName = dataRow.getString("constraint_name");

            Table dbTable = schema.getTable(dataRow.getString("table_name"));
            if (dbTable == null) {
                // Il constraint non è legato ad una tabella nota
                continue;
            }

            Constraint dbConstraint = dbTable.getConstraint(constraintName);
            if (dbConstraint == null) {
                dbConstraint = new Constraint();

                dbConstraint.setGenerated(GEN_GENERATED_NAME.equals(dataRow.getString("generated")));
                if (!dbConstraint.isGenerated()) {
                    dbConstraint.setName(dataRow.getString("constraint_name"));
                }

                dbConstraint.setType(calcConstraintType(dataRow.getString("constraint_type")));
                dbConstraint.setSearchCondition(dataRow.getString("search_condition"));
                dbConstraint.setReferenceTable(dataRow.getString("references_table"));

                // Non considero i Constrant per definire i not null sulle colonne (sono tracciati come proprietà della colonna)
                if (!dbConstraint.isGenerated() ||
                        !(dbConstraint.getType() == ConstraintType.CHECK && dbConstraint.getSearchCondition().endsWith("NOT NULL"))) {
                    dbTable.addConstraint(dbConstraint);
                }
            }

            // Aggiungo la colonna al constraint (se esiste)
            String columnName = dataRow.getString("column_name");
            if (!BaseFunction.isBlank(columnName)) {
                dbConstraint.addColumn(columnName);
            }

            if (dbConstraint.getType() == ConstraintType.PRIMARY_KEY) {
                dbTable.getTableColumn(columnName).setPrimaryKey(true);
            }
        }
    }


    @Override
    public void addStoredProcedure(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException {

        // Aggiungo la definizone delle stored procedure
        DataTable<?> dataTable = storedProcedureData(connection, owner);
        if (dataTable.size() > 0) {
            String name = "";
            String type = "";
            StringBuilder code = new StringBuilder("Create or Replace ");
            for (DataRow row : dataTable) {
                if ((!name.equals(row.getString("NAME")) || !type.equals(row.getString("TYPE"))) && name.length() > 0) {
                    if (type.equals("PACKAGE BODY")) {
                        schema.getPackage(name).setBodyDefinition(code.toString());
                    } else {
                        StoredProcedure storedProcedure = StoredProcedure.Factory.newStoredProcedure(name, StoredProcedure.Type.valueOf(type), code.toString());
                        schema.addStoredProcedure(storedProcedure);
                    }

                    code = new StringBuilder("Create or Replace ");
                }

                code.append(StringUtil.rtrim(row.getString("TEXT")) + "\n");
                name = row.getString("NAME");
                type = row.getString("TYPE");
            }

            if (type.equals("PACKAGE BODY")) {
                schema.getPackage(name).setBodyDefinition(code.toString());
            } else {
                StoredProcedure storedProcedure = StoredProcedure.Factory.newStoredProcedure(name, StoredProcedure.Type.valueOf(type), code.toString());
                schema.addStoredProcedure(storedProcedure);
            }
        }

        // Arguments
        dataTable = sourcesArguments(connection, owner);
        for (DataRow row : dataTable) {
            String objectName = row.getString("object_name");
            StoredProcedure.Type type = StoredProcedure.Type.valueOf(row.getString("object_type"));

            BigDecimal position = row.getBigDecimal("position");
            String dataType = row.getString("data_type");

            if (type == StoredProcedure.Type.FUNCTION) {
                if (position.intValue() == 0) {
                    schema.getFunction(objectName).setReturnType(dataType);
                } else {
                    schema.getFunction(objectName).addArgument(calcArgument(row));
                }
            } else if (type == StoredProcedure.Type.PROCEDURE) {
                schema.getProcedure(objectName).addArgument(calcArgument(row));
            }
        }
    }

    private Argument calcArgument(DataRow row) {
        BigDecimal position = row.getBigDecimal("position");
        String dataType = row.getString("data_type");
        String argumentName = row.getString("argument_name");
        ArgumentType argumentType = ArgumentType.valueOf(row.getString("in_out"));

        return new Argument(argumentName, dataType, argumentType, position.intValue());
    }

}

