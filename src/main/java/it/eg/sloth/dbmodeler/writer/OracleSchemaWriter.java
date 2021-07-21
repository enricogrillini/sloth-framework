package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;

public class OracleSchemaWriter extends DbSchemaAbstractWriter implements DbSchemaWriter {

    public OracleSchemaWriter(DataBaseType dataBaseType, String owner) {
        super(dataBaseType, owner);
    }

    protected String calcSize(Long size) {
        if (size == null) {
            return "64K";
        } else {
            return (size / 1024) + "K";
        }
    }

    protected String calcColumnType(String type) {
        return type;
    }

}
