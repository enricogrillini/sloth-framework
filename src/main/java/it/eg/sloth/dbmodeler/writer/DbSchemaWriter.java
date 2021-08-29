package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;

public interface DbSchemaWriter {

    DataBaseType getDataBaseType();

    String sqlDropTables(Schema schema);

    String sqlTables(Schema schema, boolean tablespace, boolean storage);

    String sqlIndexes(Schema schema, boolean tablespace, boolean storage);

    String sqlPrimaryKey(Schema schema);

    String sqlForeignKey(Schema schema);

    String sqlSequences(Schema schema);

    String sqlView(Schema schema);

    String sqlProcedure(Schema schema);

    String sqlFunction(Schema schema);

    class Factory {
        private Factory() {
            // NOP
        }

        public static DbSchemaWriter getSchemaWriter(DataBaseType dataBaseType) {
            // Imposto il reader corretto
            switch (dataBaseType) {
                case H2:
                    return new H2SchemaWriter(dataBaseType);
                case ORACLE:
                    return new OracleSchemaWriter(dataBaseType);
                case POSTGRES:
                    return new PostgresSchemaWriter(dataBaseType);

                default:
                    // NOP
            }

            return null;
        }

    }


}
