package it.eg.sloth.dbmodeler.reader;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public interface DbSchemaReader {

    DataBaseType getDataBaseType();

    String getOwner();


    default Collection<Table> loadTables(Connection connection) throws SQLException, IOException, FrameworkException {
        return loadTables(connection, null);
    }

    Collection<Table> loadTables(Connection connection, String tableName) throws SQLException, IOException, FrameworkException;

    //
//    public Views loadViews() throws SQLException, IOException, FrameworkException;
//
//    public Packages loadPackages() throws SQLException, IOException, FrameworkException;
//
    Collection<Sequence> loadSequences(Connection connection) throws SQLException, IOException, FrameworkException;


    default Schema refreshSchema(Connection connection) throws SQLException, IOException, FrameworkException {
        Schema schema = new Schema();

        // Carico le tabelle
        schema.setTableCollection(loadTables(connection));

        // Carico i sequence
        schema.setSequenceCollection(loadSequences(connection));

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

        public static DbSchemaReader getDbSchemaReader(DataBaseType dataBaseType, String owner) {
            // Imposto il reader corretto
            switch (dataBaseType) {
                case H2:
                    return new H2SchemaReader(dataBaseType, owner);
                case ORACLE:
                    return new OracleSchemaReader(dataBaseType, owner);
                case POSTGRES:
                    return new PostgresSchemaReader(dataBaseType, owner);

                default:
                    // NOP
            }

            return null;
        }
    }
}
