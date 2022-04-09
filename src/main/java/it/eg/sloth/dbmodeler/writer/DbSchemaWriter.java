package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.code.Function;
import it.eg.sloth.dbmodeler.model.schema.code.Procedure;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.view.View;

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
        schema.getTableCollection().forEach(t -> result.append(sqlIndexes(t, tablespace, storage)));

        return result.toString();
    }

    String sqlIndexes(Table table, boolean tablespace, boolean storage);

    String sqlDropRelatedForeignKeys(Schema schema, String tableName);

    String sqlRelatedForeignKeys(Schema schema, String tableName);

    default String sqlPrimaryKeys(Schema schema) {
        StringBuilder result = new StringBuilder();
        schema.getTableCollection().forEach(t -> result.append(sqlPrimaryKeys(t)));

        return result.toString();
    }

    String sqlPrimaryKeys(Table table);

    default String sqlForeignKeys(Schema schema) {
        StringBuilder result = new StringBuilder();
        schema.getTableCollection().forEach(t -> result.append(sqlForeignKeys(t)));

        return result.toString();
    }

    String sqlForeignKeys(Table table);

    default String sqlSequences(Schema schema) {
        StringBuilder result = new StringBuilder();
        result.append("-- Create sequence\n");
        schema.getSequenceCollection().forEach(t -> result.append(sqlSequence(t)));
        result.append("\n");

        return result.toString();
    }

    String sqlSequence(Sequence sequence);

    default String sqlViews(Schema schema) {
        StringBuilder result = new StringBuilder();
        schema.getViewCollection().forEach(t -> result.append(sqlView(t)));

        return result.toString();
    }

    String sqlView(View view);

    String sqlProcedures(Schema schema);

    String sqlProcedure(Procedure dbObject);

    String sqlFunctions(Schema schema);

    String sqlFunction(Function dbObject);

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
