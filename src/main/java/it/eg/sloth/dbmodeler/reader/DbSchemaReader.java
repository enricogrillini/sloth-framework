package it.eg.sloth.dbmodeler.reader;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.statistics.Statistics;
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

    <R extends DataRow> DataTable<R> constantsData(Connection connection, String tableName, String keyName) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> sequencesData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> viewsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> viewsColumnsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> storedProcedureData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    <R extends DataRow> DataTable<R> statisticsData(Connection connection, String owner) throws FrameworkException, SQLException, IOException;

    void addTables(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    void addConstraints(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException;

    void addIndexes(Schema schema, Connection connection, String owner) throws SQLException, FrameworkException, IOException;

    void addConstants(Schema schema, Connection connection) throws SQLException, FrameworkException, IOException;

    void addSequences(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    void addViews(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    void addStoredProcedure(Schema schema, Connection connection, String owner) throws SQLException, IOException, FrameworkException;

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

        // Load Tables - Constants
        addConstants(schema, connection);

        // Carico i sequence
        addSequences(schema, connection, owner);

        // Carico le viste
        addViews(schema, connection, owner);

        // Carico le stored procedure
        addStoredProcedure(schema, connection, owner);

        return schema;
    }

    Statistics refreshStatistics(Connection connection, String owner) throws SQLException, IOException, FrameworkException;

    default String writeString(DataBase dataBase) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer(new DefaultPrettyPrinter());

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataBase);
    }

    class Factory {
        private Factory() {
            // NOP
        }

        public static DbSchemaReader getDbSchemaReader(Connection connection) throws SQLException {
            Class<?> driverClass = DriverManager.getDriver(connection.getMetaData().getURL()).getClass();

            if (driverClass.getName().toLowerCase().contains("h2")) {
                return DbSchemaReader.Factory.getDbSchemaReader(DataBaseType.H2);
            } else if (driverClass.getName().toLowerCase().contains("oracle")) {
                return DbSchemaReader.Factory.getDbSchemaReader(DataBaseType.ORACLE);
            } else if (driverClass.getName().toLowerCase().contains("postgresql")) {
                return DbSchemaReader.Factory.getDbSchemaReader(DataBaseType.POSTGRES);
            } else {
                return null;
            }
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
                    return null;
            }
        }
    }
}
