package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.schema.table.TableColumn;
import lombok.Getter;

import java.text.MessageFormat;

public abstract class DbSchemaAbstractWriter {

    private static final String TABLE = "-- Create Table {0}\n" +
            "Create Table {0}\n";

    @Getter
    private DataBaseType dataBaseType;

    @Getter
    private String owner;

    protected DbSchemaAbstractWriter(DataBaseType dataBaseType, String owner) {
        this.dataBaseType = dataBaseType;
        this.owner = owner;
    }

    protected abstract String calcSize(Long size);

    protected abstract String calcColumnType(String type);

    public String sqlTables(Schema schema, boolean tablespace, boolean storage) {
        StringBuilder result = new StringBuilder();

        for (Table table : schema.getTableCollection()) {
            result.append(MessageFormat.format(TABLE, table.getName()));

            for (TableColumn tableColumn : table.getColumnCollection()) {
                if (0 == tableColumn.getPosition()) {
                    // Prima colonna
                    result.append(MessageFormat.format("      ({0} {1}", tableColumn.getName(), calcColumnType(tableColumn.getType())));
                } else {
                    // Colonna generica
                    result.append(MessageFormat.format("       {0} {1}", tableColumn.getName(), calcColumnType(tableColumn.getType())));
                }

                if (table.getColumnCollection().size() == (tableColumn.getPosition() + 1)) {
                    // Ultima colonna
                    result.append(")");
                } else {
                    // Colonna generica
                    result.append(",\n");
                }
            }

            if (tablespace) {
                result.append("\nTablespace " + table.getTablespace());
            }

            if (storage) {
                result.append(MessageFormat.format("\nStorage (Initial {0})", calcSize(table.getInitial())));
            }

            result.append(";\n\n");

        }

        return result.toString();
    }

}
