package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;

import java.text.MessageFormat;

public interface DbSchemaWriter {

    String SQL_SEQUENCE = "Create Sequence {0};\n";

    DataBaseType getDataBaseType();

    String getOwner();

    String sqlTables(Schema schema, boolean tablespace, boolean storage);

    default String sqlSequences(Schema schema) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-- Create sequence\n");
        if (schema != null && schema.getSequenceCollection() != null) {
            for (Sequence sequence : schema.getSequenceCollection()) {
                stringBuilder.append(sqlSequence(sequence));
            }
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    default String sqlSequence(Sequence sequence) {
        return MessageFormat.format(SQL_SEQUENCE, sequence.getName());
    }

    class Factory {
        private Factory() {
            // NOP
        }

        public static DbSchemaWriter getDbSchemaReader(DataBaseType dataBaseType, String owner){
            // Imposto il reader corretto
            switch (dataBaseType) {
                case H2:
                    return new H2SchemaWriter(dataBaseType, owner);
                case ORACLE:
                    return new OracleSchemaWriter(dataBaseType, owner);
                case POSTGRES:
                    return new PostgresSchemaWriter(dataBaseType, owner);

                default:
                    // NOP
            }

            return null;
        }

    }


}
