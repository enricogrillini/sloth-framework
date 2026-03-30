package it.eg.sloth.framework.utility.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.Button;
import it.eg.sloth.form.fields.field.impl.Hidden;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

public class GridUtil {

    public static void copyFromDataSourceGridToFields(Grid<?> grid, Fields<?> fields) throws FrameworkException {
        fields.removeChilds();

        DataTable<?> dataTable = grid.getDataSource();
        if (dataTable.size() > 0) {
            int rowNumber = 0;
            for (DataRow dataRow : dataTable) {
                Grid<?> appGrid = grid.newInstance();
                appGrid.copyFromDataSource(dataRow);

                for (SimpleField field : appGrid.getElements()) {
                    field.setName(field.getName() + "|" + rowNumber);

                    if (field instanceof TextField && ((TextField<?>) field).isHidden() || field instanceof Hidden<?>) {
                        continue;
                    }

                    if (field instanceof Button button) {
                        button.setIndex(rowNumber);
                    }

                    fields.addChild(field);
                }

                rowNumber++;
            }
        }
    }


    public static void copyFromDataSourceFieldsToGrid(Grid<?> grid, Fields<?> fields) throws FrameworkException {
        DataTable<?> dataTable = grid.getDataSource();

        for (SimpleField field : fields) {
            String rownumber = StringUtil.split(field.getName(), "|")[1];

            dataTable.setCurrentRow(Integer.valueOf(rownumber));

            if (field instanceof TextField textField) {
                dataTable.setObject(textField.getAlias(), textField.getValue());
            }
        }
    }
}
