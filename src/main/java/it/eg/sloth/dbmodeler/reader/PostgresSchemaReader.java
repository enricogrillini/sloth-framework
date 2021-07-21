package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
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
            "       c.data_type,\n" +
            "       c.character_maximum_length\n" +
            "From pg_class t\n" +
            "     Inner Join pg_namespace n on t.relnamespace = n.oid\n" +
            "     Inner Join information_schema.columns c  on t.relname = c.table_name\n" +
            "Where t.relkind = 'r'\n" +
            "/*W*/\n" +
            "Order By t.relname, c.ordinal_position";

    private static final String SQL_DB_SEQUENCES = "Select c.relname sequence_name \n" +
            "From pg_class c\n" +
            "     Inner Join pg_namespace n on c.relnamespace = n.oid\n" +
            "Where c.relkind = 'S' And\n" +
            "      n.nspname = ?\n" +
            "Order By 1";

    public PostgresSchemaReader(DataBaseType dataBaseType, String owner) {
        super(dataBaseType, owner);
    }

    @Override
    protected <R extends DataRow> DataTable<R> tablesData(Connection connection, String tableName) throws FrameworkException, SQLException, IOException {
        FilteredQuery query = new FilteredQuery(SQL_DB_TABLES);
        query.addFilter("n.nspname = ?", Types.VARCHAR, getOwner());
        query.addFilter("t.table_schema = ?", Types.VARCHAR, tableName);

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
        if (dataRow.getBigDecimal("character_maximum_length") == null) {
            return dataRow.getString("COLUMN_TYPE");
        } else {
            return dataRow.getString("COLUMN_TYPE") + "(" + dataRow.getBigDecimal("character_maximum_length").intValue() + ")";
        }
    }
}
