package it.eg.sloth.dbmodeler.reader;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.reader.h2.H2SchemaReader;
import it.eg.sloth.dbmodeler.reader.oracle.OracleSchemaReader;
import it.eg.sloth.dbmodeler.reader.postgres.PostgresSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface DbSchemaReader {

    Connection getJdbcConnection() throws SQLException;

    //    public Tables loadTables() throws SQLException, IOException, FrameworkException;
//
//    public Views loadViews() throws SQLException, IOException, FrameworkException;
//
//    public Packages loadPackages() throws SQLException, IOException, FrameworkException;
//
    Collection<Sequence> loadSequences() throws SQLException, IOException, FrameworkException;

    default boolean testConnection() throws SQLException {
        return getJdbcConnection() != null;
    }

    default void refreshSchema(DataBase dataBase) throws SQLException, IOException, FrameworkException {
        // Pulisco lo schema
        dataBase.setSchema(new Schema());

        if (testConnection()) {
            // Carico i sequence
            dataBase.getSchema().addSequence(loadSequences());
        }
    }

    default void writeFile(String fileName, DataBase dataBase) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(fileName), dataBase);
    }

    default String writeString(DataBase dataBase) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer(new DefaultPrettyPrinter());

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataBase);
    }

    public static class Factory {
        private Factory() {
            // NOP
        }

        public static DbSchemaReader getDbSchemaReader(DbConnection dbConnection) {
            // Imposto il reader corretto
            switch (dbConnection.getDataBaseType()) {
                case H2:
                    return new H2SchemaReader(dbConnection);
                case ORACLE:
                    return new OracleSchemaReader(dbConnection);
                case POSTGRES:
                    return new PostgresSchemaReader(dbConnection);

                default:
                    // NOP
            }

            return null;
        }

    }


}
