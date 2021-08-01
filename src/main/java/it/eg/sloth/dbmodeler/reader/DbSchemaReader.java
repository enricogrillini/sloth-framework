package it.eg.sloth.dbmodeler.reader;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DbSchemaReader {

    DataBaseType getDataBaseType();

    String calcColumnType(DataRow dataRow);

    <R extends DataRow> DataTable<R> tablesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> constraintsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> indexesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> sequencesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    void addTables(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    void addConstraints(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException;

    void addIndexes(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException;

    void addSequences(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    default Schema refreshSchemaByDbConnection(DbConnection dbConnection) throws SQLException, IOException, FrameworkException {
        DriverManager.setLoginTimeout(1);
        Connection connection = DriverManager.getConnection(dbConnection.getJdbcUrl(), dbConnection.getDbUser(), dbConnection.getDbPassword());

        return refreshSchema(connection, dbConnection.getDbOwner());
    }

    default Schema refreshSchema(Connection connection, String owner) throws SQLException, IOException, FrameworkException {
        Schema schema = new Schema();

        // Load Tables
        addTables(schema, connection, owner);

        // Load Tables - Constraints
        addConstraints(schema, connection, owner);

        // Load Tables - Indexes
        addIndexes(schema, connection, owner);

        // Carico i sequence
        addSequences(schema, connection, owner);

        return schema;
    }

    default String writeString(DataBase dataBase) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer(new DefaultPrettyPrinter());

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataBase);
    }

    class Factory {
        private Factory() {
            // NOP
        }

        public static DbSchemaReader getDbSchemaReader(DataBaseType dataBaseType) {
            // Imposto il reader corretto
            switch (dataBaseType) {
                case H2:
                    return new H2SchemaReader(dataBaseType);
                case ORACLE:
                    return new OracleSchemaReader(dataBaseType);
                case POSTGRES:
                    return new PostgresSchemaReader(dataBaseType);
                default:
                    // NOP
            }

            return null;
        }
    }
}
