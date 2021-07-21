package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class H2SchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String SQL_DB_TABLES = "Select t.table_name,\n" +
            "       '' table_comments,\n" +
            "       '' tablespace_name,\n" +
            "       0 initial_extent,\n" +
            "       'N' temporary_table,\n" +
            "       '' Duration,\n" +
            "       c.column_name,\n" +
            "       '' column_comments,\n" +
            "       substr(IS_NULLABLE, 1, 1) nullable,\n" +
            "       --\n" +
            "       c.DATA_TYPE,\n" +
            "       c.CHARACTER_MAXIMUM_LENGTH,\n" +
            "       c.COLUMN_TYPE\n" +
            "from INFORMATION_SCHEMA.TABLES t\n" +
            "     Inner Join INFORMATION_SCHEMA.COLUMNS c on t.table_schema = c.table_schema And  t.table_name = c.table_name\n" +
            "/*W*/\n" +
            "Order by table_name, ordinal_position";

    private static final String SQL_DB_SEQUENCES = "Select sequence_name from INFORMATION_SCHEMA.SEQUENCES Where sequence_Schema = ? Order by sequence_name";


    public H2SchemaReader(DataBaseType dataBaseType, String owner) {
        super(dataBaseType, owner);
    }

    @Override
    protected <R extends DataRow> DataTable<R> tablesData(Connection connection, String tableName) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_TABLES);
        query.addFilter("t.table_schema = ?", Types.VARCHAR, getOwner());
        query.addFilter("t.table_name = ?", Types.VARCHAR, tableName);

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
        return dataRow.getString("COLUMN_TYPE").replace(" NOT NULL", "");
    }
}
