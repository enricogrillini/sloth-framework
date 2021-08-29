package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.text.MessageFormat;

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

    private static final String SQL_CONSTANTS = "select * from {0} Order by 1";

    private static final String SQL_DB_SEQUENCES = "Select sequence_name from INFORMATION_SCHEMA.SEQUENCES Where sequence_Schema = ? Order by sequence_name";

    private static final String SQL_STATS = "select *\n" +
            "From (select count(*) table_count,\n" +
            "             sum(DISK_SPACE_USED (t.table_schema || '.' || t.table_name)) table_size\n" +
            "      from INFORMATION_SCHEMA.TABLES t\n" +
            "      where t.table_schema = ?) table_stats,\n" +
            "     (select count(distinct index_name) index_count,\n" +
            "             null index_size\n" +
            "      from INFORMATION_SCHEMA.INDEXES t\n" +
            "      where t.table_schema = ?) index_stats";


    public H2SchemaReader(DataBaseType dataBaseType) {
        super(dataBaseType);
    }

    @Override
    public <R extends DataRow> DataTable<R> tablesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_TABLES);
        query.addFilter("t.table_schema = ?", Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public <R extends DataRow> DataTable<R> constraintsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        return (DataTable<R>) new Table();
    }

    @Override
    public <R extends DataRow> DataTable<R> indexesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        return (DataTable<R>) new Table();
    }

    @Override
    public <R extends DataRow> DataTable<R> constantsData(Connection connection, String tableName, String keyName) throws FrameworkException, SQLException, IOException {
        Query query = new Query(MessageFormat.format(SQL_CONSTANTS, tableName));

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
        return (DataTable<R>) new Table();
    }

    @Override
    public <R extends DataRow> DataTable<R> viewsColumnsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        return (DataTable<R>) new Table();
    }

    @Override
    public <R extends DataRow> DataTable<R> storedProcedureData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        return (DataTable<R>) new Table();
    }

    @Override
    public <R extends DataRow> DataTable<R> statisticsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException {
        Query query = new Query(SQL_STATS);
        query.addParameter(Types.VARCHAR, owner);
        query.addParameter(Types.VARCHAR, owner);

        return query.selectTable(connection);
    }

    @Override
    public void addConstraints(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException {

    }

    @Override
    public void addStoredProcedure(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException {
        // NOP
    }

    @Override
    public String calcColumnType(DataRow dataRow) {
        return dataRow.getString("COLUMN_TYPE").replace(" NOT NULL", "");
    }
}
