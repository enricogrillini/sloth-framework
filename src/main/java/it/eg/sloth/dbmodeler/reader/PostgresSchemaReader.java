package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.table.Constraint;
import it.eg.sloth.dbmodeler.model.schema.table.ConstraintType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

@Slf4j
public class PostgresSchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String SQL_DB_TABLES = "Select t.relname table_name,\n" +
            "       obj_description(t.oid) table_comments,\n" +
            "       '' tablespace_name,\n" +
            "       0 initial_extent,\n" +
            "       'N' temporary_table,\n" +
            "       '' Duration,\n" +
            "       c.column_name,\n" +
            "       col_description(t.oid, c.ordinal_position) column_comments,\n" +
            "       substr(IS_NULLABLE, 1, 1) nullable,\n" +
            "       --\n" +
            "       c.data_type COLUMN_TYPE,\n" +
            "       c.character_maximum_length\n" +
            "From pg_class t\n" +
            "     Inner Join pg_namespace n on t.relnamespace = n.oid\n" +
            "     Inner Join information_schema.columns c on t.relname = c.table_name And n.nspname = c.table_schema\n" +
            "Where t.relkind = 'r'\n" +
            "/*W*/\n" +
            "Order By t.relname, c.ordinal_position";

    private static final String SQL_DB_CONSTRAINT = "SELECT rel.relname table_name,\n" +
            "       conname constraint_name,\n" +
            "       pg_get_expr(conbin, conrelid) search_condition,\n" +
            "       rel2.relname references_table,\n" +
            "       contype constraint_type,\n" +
            "       array_to_string(conkey, ',') constraint_column_index\n" +
            "FROM pg_catalog.pg_constraint con\n" +
            "     INNER JOIN pg_catalog.pg_class rel ON rel.oid = con.conrelid\n" +
            "     INNER JOIN pg_catalog.pg_namespace n ON n.oid = connamespace\n" +
            "     LEFT JOIN pg_catalog.pg_class rel2 ON rel2.oid = con.confrelid\n" +
            "/*W*/\n" +
            "Order By rel.relname, conname";

    private static final String SQL_DB_INDEXES = "select i.relname as index_name,\n" +
            "       a.attnum as column_position,\n" +
            "       t.relname as table_name,\n" +
            "       CASE WHEN ix.indisunique THEN 'Y' ELSE 'N'END as uniqueness,\n" +
            "       a.attname as column_name,\n" +
            "       '' tablespace_name,\n" +
            "       0 initial_extent\n" +
            "from pg_index ix\n" +
            "     inner join pg_class i on i.oid = ix.indexrelid\n" +
            "     inner join pg_class t on t.oid = ix.indrelid And t.relkind = 'r'\n" +
            "     inner join pg_attribute a on a.attrelid = t.oid And a.attnum = ANY(ix.indkey)\n" +
            "     inner join pg_namespace n on i.relnamespace = n.oid\n" +
            "/*W*/\n" +
            "Order By t.relname, i.relname, a.attnum";

    private static final String SQL_DB_SEQUENCES = "Select c.relname sequence_name \n" +
            "From pg_class c\n" +
            "     Inner Join pg_namespace n on c.relnamespace = n.oid\n" +
            "Where c.relkind = 'S' And\n" +
            "      n.nspname = ?\n" +
            "Order By 1";

    private static final String SQL_DB_PROC_FUNCTIONS = "select n.nspname as schema_name,\n" +
            "       p.proname as specific_name,\n" +
            "       case p.prokind \n" +
            "            when 'f' then 'FUNCTION'\n" +
            "            when 'p' then 'PROCEDURE'\n" +
            "            when 'a' then 'AGGREGATE'\n" +
            "            when 'w' then 'WINDOW'\n" +
            "            end as kind,\n" +
            "       l.lanname as language,\n" +
            "       case when l.lanname = 'internal' then p.prosrc\n" +
            "            else pg_get_functiondef(p.oid)\n" +
            "            end as definition,\n" +
            "       pg_get_function_arguments(p.oid) as arguments,\n" +
            "       t.typname as return_type\n" +
            "from pg_proc p\n" +
            "     left join pg_namespace n on p.pronamespace = n.oid\n" +
            "     left join pg_language l on p.prolang = l.oid\n" +
            "     left join pg_type t on t.oid = p.prorettype \n" +
            "where n.nspname = ?\n" +
            "order by schema_name,\n" +
            "         specific_name";

    private static final String SQL_STATS = "select *\n" +
            "From (select sum(pg_relation_size(relid)) table_size, \n" +
            "             count(*) table_count\n" +
            "      From pg_catalog.pg_stat_all_tables t\n" +
            "      Where t.schemaname = ?) tableStats,\n" +
            "     (select sum(pg_relation_size(relid)) index_size,\n" +
            "             count(*) index_count\n" +
            "      From pg_catalog.pg_stat_all_indexes t\n" +
            "      Where t.schemaname = ?) indexStats";

    public PostgresSchemaReader(DataBaseType dataBaseType) {
        super(dataBaseType);
    }

    @Override
    public <R extends DataRow> DataTable<R> tablesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_TABLES);
        query.addFilter("n.nspname = ?", Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> constraintsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_CONSTRAINT);
        query.addFilter("n.nspname = ?", Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> indexesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_INDEXES);
        query.addFilter("n.nspname = ?", Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> sequencesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_SEQUENCES);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> statisticsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_STATS);
        query.addParameter(Types.VARCHAR, owner);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public String calcColumnType(DataRow dataRow) {
        if (dataRow.getBigDecimal("character_maximum_length") == null) {
            return dataRow.getString("COLUMN_TYPE");
        } else {
            return dataRow.getString("COLUMN_TYPE") + "(" + dataRow.getBigDecimal("character_maximum_length").intValue() + ")";
        }
    }

    private ConstraintType calcConstraintType(String type) {
        if ("p".equals(type)) {
            return ConstraintType.PRIMARY_KEY;
        } else if ("f".equals(type)) {
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
            Table dbTable = schema.getTable(dataRow.getString("table_name"));
            if (dbTable == null) {
                // Il constraint non è legato ad una tabella nota
                continue;
            }

            Constraint dbConstraint = new Constraint();
            dbConstraint.setGenerated(false);
            dbConstraint.setName(dataRow.getString("constraint_name"));
            dbConstraint.setType(calcConstraintType(dataRow.getString("constraint_type")));
            dbConstraint.setSearchCondition(dataRow.getString("search_condition"));
            dbConstraint.setReferenceTable(dataRow.getString("references_table"));

            // Aggiungo le colonne al constraint (se esiste)
            String constraintColumnIndex = dataRow.getString("constraint_column_index");
            if (constraintColumnIndex != null) {
                for (String strIndex : StringUtil.split(constraintColumnIndex)) {
                    int index = Integer.parseInt(strIndex) - 1;
                    dbConstraint.addColumn(dbTable.getTableColumn(index).getName());
                    if (dbConstraint.getType() == ConstraintType.PRIMARY_KEY) {
                        dbTable.getTableColumn(index).setPrimaryKey(true);
                    }
                }
            }

            dbTable.addConstraint(dbConstraint);
        }
    }


}


