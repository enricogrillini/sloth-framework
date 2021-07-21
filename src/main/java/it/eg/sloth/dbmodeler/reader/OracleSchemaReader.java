package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class OracleSchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String SQL_DB_TABLES = "Select t.table_name,\n" +
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
            "      t.table_name = nvl(upper(?), t.table_name) And\n" +
            // Escludo dalle tabelle di sistema per import/export
            "      t.table_name not like 'SYS_EXPORT_SCHEMA%' And\n" +
            "      t.table_name not like 'SYS_IMPORT_FULL%' And\n" +

            // Escludo dalle tabelle le viste materializzate:
            "      Not Exists (Select 1 From ALL_mviews mv Where mv.owner = t.owner And mv.mview_name = t.table_name)\n" +
            "/*W*/\n" +
            "Order by t.table_Name, tc.column_id";

    private static final String SQL_DB_SEQUENCES = "Select sequence_name\n" +
            "From all_sequences\n" +
            "Where sequence_owner = upper(?)\n" +
            "Order By sequence_name";


    public OracleSchemaReader(DataBaseType dataBaseType, String owner) {
        super(dataBaseType, owner);
    }

    @Override
    protected <R extends DataRow> DataTable<R> tablesData(Connection connection, String tableName) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_TABLES);
        query.addParameter(Types.VARCHAR, getOwner());
        query.addParameter(Types.VARCHAR, tableName);

        return query.selectTable(connection);
    }

    @Override
    protected <R extends DataRow> DataTable<R> sequencesData(Connection connection) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_DB_SEQUENCES);
        query.addParameter(Types.VARCHAR, getOwner());

        return query.selectTable(connection);
    }

    @Override
    protected String calcColumnType(DataRow dataRow) {
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

}

