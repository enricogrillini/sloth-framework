package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.framework.common.base.StringUtil;

public class H2SchemaWriter extends DbSchemaAbstractWriter implements DbSchemaWriter {

    public H2SchemaWriter(DataBaseType dataBaseType, String owner) {
        super(dataBaseType, owner);
    }

    @Override
    public String sqlTables(Schema schema, boolean tablespace, boolean storage) {
        return "";
    }

    protected String calcSize(Long size) {
        // Lo storage per i DB H2 non è gestito
        return StringUtil.EMPTY;
    }

    protected String calcColumnType(String type) {
        return type;
    }

}