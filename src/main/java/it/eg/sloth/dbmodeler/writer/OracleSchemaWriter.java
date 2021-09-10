package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;

public class OracleSchemaWriter extends DbSchemaAbstractWriter implements DbSchemaWriter {

    public OracleSchemaWriter(DataBaseType dataBaseType) {
        super(dataBaseType);
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

    @Override
    public String sqlIndexes(Schema schema, boolean tablespace, boolean storage) {
        return super.sqlIndexes(schema, false, tablespace, tablespace);
    }

}