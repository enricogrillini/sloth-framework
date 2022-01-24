package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.code.Function;
import it.eg.sloth.dbmodeler.model.schema.table.Table;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
            result.append(sqlIndexes(table, tablespace, storage));
        }

        return result.toString();
    }

    String sqlIndexes(Table table, boolean tablespace, boolean storage);

    String sqlDropRelatedForeignKeys(Schema schema, String tableName);

    String sqlRelatedForeignKeys(Schema schema, String tableName);

    String sqlPrimaryKeys(Schema schema);

    String sqlForeignKeys(Schema schema);

    String sqlSequences(Schema schema);

    String sqlView(Schema schema);

    String sqlProcedures(Schema schema);

    String sqlFunctions(Schema schema);

    String sqlFunction(Function table);

    String sqlPackages(Schema schema);

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
