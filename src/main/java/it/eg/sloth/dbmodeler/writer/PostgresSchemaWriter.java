package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.framework.common.base.StringUtil;

public class PostgresSchemaWriter extends DbSchemaAbstractWriter implements DbSchemaWriter {

    public PostgresSchemaWriter(DataBaseType dataBaseType) {
        super(dataBaseType);
    }

    protected String calcSize(Long size) {
        // Lo storage per i DB H2 non è gestito
        return StringUtil.EMPTY;
    }

    protected String calcColumnType(String type) {
        if (type.equalsIgnoreCase("NUMBER(38,0)")) {
            return "INTEGER";
        } else if (type.startsWith("VARCHAR2")) {
            return type.replace("VARCHAR2", "VARCHAR");
        } else if (type.startsWith("FLOAT")) {
            return "FLOAT";
        } else if (type.equalsIgnoreCase("CLOB")) {
            return "TEXT";
        } else if (type.equalsIgnoreCase("BLOB")) {
            return "BYTEA";
        } else {
            return type;
        }
    }

    @Override
    public String sqlTables(Schema schema, boolean tablespace, boolean storage) {
        return super.sqlTables(schema, tablespace, false);
    }

    @Override
    public String sqlIndexes(Schema schema, boolean tablespace, boolean storage) {
        return super.sqlIndexes(schema, true, tablespace, false);
    }
}
