package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.framework.common.base.StringUtil;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
public class H2SchemaWriter extends DbSchemaAbstractWriter implements DbSchemaWriter {

    private static final String EOS = "";

    public H2SchemaWriter() {
        super(DataBaseType.H2, EOS);
    }

    protected String calcSize(Long size) {
        // Lo storage per i DB H2 non è gestito
        return StringUtil.EMPTY;
    }

    protected String calcColumnType(String type) {
        return type;
    }

    @Override
    public String sqlTables(Schema schema, boolean tablespace, boolean storage) {
        return super.sqlTables(schema, tablespace, false);
    }

    @Override
    public String sqlIndexes(Table table, boolean tablespace, boolean storage) {
        return super.sqlIndexes(table, true, tablespace, false);
    }
}