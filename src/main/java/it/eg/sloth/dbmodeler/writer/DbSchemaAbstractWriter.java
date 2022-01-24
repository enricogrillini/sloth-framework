package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.code.Function;
import it.eg.sloth.dbmodeler.model.schema.code.Package;
import it.eg.sloth.dbmodeler.model.schema.code.Procedure;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.model.schema.table.*;
import it.eg.sloth.dbmodeler.model.schema.view.View;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Getter;

import java.text.MessageFormat;

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
public abstract class DbSchemaAbstractWriter implements DbSchemaWriter {

    private static final String DROP_TABLE = "Drop Table {0};\n";

    private static final String TABLE = "-- Create Table {0}\n" +
            "Create Table {0}\n";

    private static final String SQL_COLUMN_COMMENT = "Comment On Column {0}.{1} Is ''{2}'';\n";


    private static final String INDEX = "-- Create Index {0}\n" +
            "Create {2}Index {0} on {1} ({3})";

    private static final String SQL_PRIMARY_KEY = "-- Primary Key {0}\n" +
            "Alter Table {0} Add Constraint {1} Primary Key ({2});\n" +
            "\n";

    private static final String SQL_FOREIGN_KEY = "-- Foreign Key {0}\n" +
            "Alter Table {0} Add Constraint {1} Foreign Key ({2}) references {3};\n" +
            "\n";

    private static final String SQL_DROP_FOREIGN_KEY = "-- Drop Foreign Key {0}\n" +
            "Alter Table {0} Drop Constraint {1};\n" +
            "\n";

    private static final String SQL_SEQUENCE = "Create Sequence {0};\n";

    private static final String SQL_VIEW = "-- View {0}\n" +
            "Create or replace view {0} As\n" +
            "{1}\n{2}\n" +
            "\n";

    private static final String SQL_PROCEDURE = "-- Procedure {0}\n" +
            "{1}{2}\n" +
            "\n";

    private static final String SQL_FUNCTION = "-- Function {0}\n" +
            "{1}{2}\n" +
            "\n";

    private static final String SQL_PACKAGE = "-- Package {0}\n" +
            "{1}{3}\n" +
            "\n" +
            "{2}{3}\n" +
            "\n";

    @Getter
    private DataBaseType dataBaseType;

    @Getter
    private String endOfStatement;

    protected DbSchemaAbstractWriter(DataBaseType dataBaseType, String endOfStatement) {
        this.dataBaseType = dataBaseType;
        this.endOfStatement = endOfStatement;
    }


    protected abstract String calcSize(Long size);

    protected abstract String calcColumnType(String type);

    @Override
    public String sqlDropTables(Schema schema) {
        StringBuilder result = new StringBuilder();
        result.append("-- Drop Table\n");
        for (Table table : schema.getTableCollection()) {
            result.append(MessageFormat.format(DROP_TABLE, table.getName()));
        }
        result.append("\n");

        return result.toString();
    }

    @Override
    public String sqlTable(Table table, boolean tablespace, boolean storage) {
        StringBuilder result = new StringBuilder();

        result.append(MessageFormat.format(TABLE, table.getName()));
        for (TableColumn tableColumn : table.getTableColumnCollection()) {
            if (0 == tableColumn.getPosition()) {
                // Prima colonna
                result.append(MessageFormat.format("      ({0} {1}{2}", tableColumn.getName(), calcColumnType(tableColumn.getType()), tableColumn.isNullable() ? "" : " Not Null"));
            } else {
                // Colonna generica
                result.append(MessageFormat.format("       {0} {1}{2}", tableColumn.getName(), calcColumnType(tableColumn.getType()), tableColumn.isNullable() ? "" : " Not Null"));
            }

            if (table.getTableColumnCollection().size() == (tableColumn.getPosition() + 1)) {
                // Ultima colonna
                result.append(")");
            } else {
                // Colonna generica
                result.append(",\n");
            }
        }

        if (tablespace && !BaseFunction.isBlank(table.getTablespace())) {
            result.append("\nTablespace " + table.getTablespace());
        }

        if (storage && !BaseFunction.isNull(table.getInitial())) {
            result.append(MessageFormat.format("\nStorage (Initial {0})", calcSize(table.getInitial())));
        }

        result.append(";\n\n");

        // Commenti
        for (TableColumn tableColumn : table.getTableColumnCollection()) {
            result.append(MessageFormat.format(SQL_COLUMN_COMMENT, table.getName(), tableColumn.getName(), BaseFunction.nvl(tableColumn.getDescription(), "")));
        }

        result.append("\n");

        return result.toString();
    }

    public String sqlIndexes(Table table, boolean excludePrimaryKey, boolean tablespace, boolean storage) {
        StringBuilder result = new StringBuilder();
        for (Index index : table.getIndexCollection()) {
            // Esclusione degli indici da Primary Key
            if (excludePrimaryKey && table.getConstraint(index.getName()) != null && table.getConstraint(index.getName()).getType() == ConstraintType.PRIMARY_KEY) {
                continue;
            }

            result.append(MessageFormat.format(INDEX, index.getName(), table.getName(), index.isUniqueness() ? "Unique " : "", StringUtil.join(index.getColumns())));

            if (tablespace && !BaseFunction.isBlank(index.getTablespace())) {
                result.append("\nTablespace " + index.getTablespace());
            }

            if (storage && !BaseFunction.isNull(index.getInitial())) {
                result.append(MessageFormat.format("\nStorage (Initial {0})", calcSize(index.getInitial())));
            }

            result.append(";\n\n");
        }
        return result.toString();
    }

    @Override
    public String sqlDropRelatedForeignKeys(Schema schema, String tableName) {
        StringBuilder result = new StringBuilder();
        for (Table table : schema.getTableCollection()) {
            for (Constraint constraint : table.getConstraintCollection()) {
                if (constraint.getType() == ConstraintType.FOREIGN_KEY && constraint.getReferenceTable().equalsIgnoreCase(tableName)) {
                    result.append(MessageFormat.format(SQL_DROP_FOREIGN_KEY,
                            table.getName(),
                            constraint.getName()));
                }
            }
        }

        return result.toString();
    }

    @Override
    public String sqlRelatedForeignKeys(Schema schema, String tableName) {
        StringBuilder result = new StringBuilder();
        for (Table table : schema.getTableCollection()) {
            for (Constraint constraint : table.getConstraintCollection()) {
                if (constraint.getType() == ConstraintType.FOREIGN_KEY && constraint.getReferenceTable().equalsIgnoreCase(tableName)) {
                    result.append(MessageFormat.format(SQL_FOREIGN_KEY,
                            table.getName(),
                            constraint.getName(),
                            StringUtil.join(constraint.getColumns().toArray(new String[0])),
                            constraint.getReferenceTable()));
                }
            }
        }

        return result.toString();
    }

    @Override
    public String sqlPrimaryKeys(Schema schema) {
        StringBuilder result = new StringBuilder();

        for (Table table : schema.getTableCollection()) {
            for (Constraint constraint : table.getConstraintCollection()) {
                if (ConstraintType.PRIMARY_KEY == constraint.getType()) {
                    result.append(MessageFormat.format(SQL_PRIMARY_KEY,
                            table.getName(),
                            constraint.getName(),
                            StringUtil.join(constraint.getColumns().toArray(new String[0]))));
                }
            }
        }

        return result.toString();
    }

    @Override
    public String sqlForeignKeys(Schema schema) {
        StringBuilder result = new StringBuilder();
        for (Table table : schema.getTableCollection()) {
            for (Constraint constraint : table.getConstraintCollection()) {
                if (ConstraintType.FOREIGN_KEY == constraint.getType()) {
                    result.append(MessageFormat.format(SQL_FOREIGN_KEY,
                            table.getName(),
                            constraint.getName(),
                            StringUtil.join(constraint.getColumns().toArray(new String[0])),
                            constraint.getReferenceTable()));
                }
            }
        }

        return result.toString();
    }

    @Override
    public String sqlSequences(Schema schema) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("-- Create sequence\n");
        if (schema != null && schema.getSequenceCollection() != null) {
            for (Sequence sequence : schema.getSequenceCollection()) {
                stringBuilder.append(MessageFormat.format(SQL_SEQUENCE, sequence.getName()));
            }
        }
        stringBuilder.append("\n");

        return stringBuilder.toString();
    }

    @Override
    public String sqlView(Schema schema) {
        StringBuilder result = new StringBuilder();
        for (View dbObject : schema.getViewCollection()) {
            result.append(MessageFormat.format(SQL_VIEW, dbObject.getName(), dbObject.getDefinition(), getEndOfStatement()));
        }

        return result.toString();
    }

    @Override
    public String sqlProcedures(Schema schema) {
        StringBuilder result = new StringBuilder();
        for (Procedure dbObject : schema.getProcedureCollection()) {
            result.append(MessageFormat.format(SQL_PROCEDURE, dbObject.getName(), dbObject.getDefinition(), getEndOfStatement()));
        }

        return result.toString();
    }

    @Override
    public String sqlFunctions(Schema schema) {
        StringBuilder result = new StringBuilder();
        for (Function dbObject : schema.getFunctionCollection()) {
            result.append(sqlFunction(dbObject));
        }

        return result.toString();
    }

    @Override
    public String sqlFunction(Function dbObject) {
        return MessageFormat.format(SQL_FUNCTION, dbObject.getName(), dbObject.getDefinition(), getEndOfStatement());
    }

    @Override
    public String sqlPackages(Schema schema) {
        StringBuilder result = new StringBuilder();
        for (Package dbObject : schema.getPackageCollection()) {
            result.append(MessageFormat.format(SQL_PACKAGE, dbObject.getName(), dbObject.getDefinition(), dbObject.getBodyDefinition(), getEndOfStatement()));
        }

        return result.toString();
    }

}
