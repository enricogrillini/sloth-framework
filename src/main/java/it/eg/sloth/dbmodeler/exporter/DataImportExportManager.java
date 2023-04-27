package it.eg.sloth.dbmodeler.exporter;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.schema.table.Column;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataImportExportManager {

    private DataImportExportManager() {
        // NOP
    }

    public static Grid getGrid(Table table) {
        Grid<?> result = new Grid<>(table.getName(), table.getName());

        for (Column column : table.getTableColumnCollection()) {
            DataTypes dataType = getDataType(column.getType());

            Text<?> text = new Text<>(column.getName(), column.getName(), dataType);
            result.addChild(text);
        }

        return result;
    }

    public static DataTable getData(Table table, Connection connection) throws FrameworkException, SQLException, IOException {
        Query query = new Query("Select * from " + table.getName() + " Order by 1");

        return query.selectTable(connection);
    }

    public static DataTypes getDataType(String type) {
        String dataType = type.toUpperCase();

        if (dataType.startsWith("NUMBER") || dataType.startsWith("DOUBLE") || dataType.startsWith("FLOAT") || dataType.startsWith("BIT") || dataType.startsWith("BIGINT") || dataType.startsWith("INT") || dataType.startsWith("TINYINT") || dataType.startsWith("UNIQUEIDENTIFIER") || dataType.startsWith("DECIMAL")) {
            return DataTypes.DECIMAL;
        } else if (dataType.startsWith("DATE")) {
            return DataTypes.DATE;
        } else if (dataType.startsWith("TIMESTAMP")) {
            return DataTypes.DATETIME;
        } else if (dataType.startsWith("VARCHAR") || dataType.startsWith("CHAR") || dataType.startsWith("LONG") || dataType.startsWith("TEXT")) {
            return DataTypes.STRING;
        } else if (dataType.startsWith("BLOB") || dataType.startsWith("BYTEA")) {
            return null;
        } else if (dataType.startsWith("CLOB")) {
            return null;
        } else if (dataType.startsWith("BOOL")) {
            return DataTypes.STRING;
        } else {
            return null;
        }
    }

}
