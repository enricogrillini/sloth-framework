package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.table.Table;

public interface DbSchemaWriter {

    DataBaseType getDataBaseType();

    String sqlDropTables(Schema schema);

    default String sqlTables(Schema schema, boolean tablespace, boolean storage) {
        StringBuilder result = new StringBuilder();
        schema.getTableCollection().forEach(t -> result.append(sqlTable(t, tablespace, storage)));

        return result.toString();
    }

    String sqlTable(Table table, boolean tablespace, boolean storage);

    default String sqlIndexes(Schema schema, boolean tablespace, boolean storage) {
        StringBuilder result = new StringBuilder();

        for (Table table : schema.getTableCollection()) {
            result.append(sqlIndexes(table,  tablespace, storage));
        }

        return result.toString();
    }

    String sqlIndexes(Table table,  boolean tablespace, boolean storage);

    String sqlPrimaryKeys(Schema schema);

    String sqlForeignKeys(Schema schema);

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
