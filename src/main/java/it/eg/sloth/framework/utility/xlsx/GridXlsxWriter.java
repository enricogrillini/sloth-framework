package it.eg.sloth.framework.utility.xlsx;

import java.util.Locale;

import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.Semaphore;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelType;

public class GridXlsxWriter extends BaseXlsxWriter {

    protected int rowIndex;

    public GridXlsxWriter() {
        super();
        rowIndex = 0;
    }

    public GridXlsxWriter(boolean title, Grid<?>... grids) throws FrameworkException {
        super();

        for (int i = 0; i < grids.length; i++) {
            addGrid(title, grids[i]);
        }
    }

    public void setCell(int row, int cellindex, DataField<?> dataField) {
        if (dataField instanceof Semaphore) {
            Semaphore semaforo = (Semaphore) dataField;

            setCellValue(row, cellindex, semaforo.getDecodedText());
            if (Semaphore.WHITE.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, null);
            } else if (Semaphore.GREEN.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.GREEN_BORDERED, null, null);
            } else if (Semaphore.YELLOW.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.YELLOW_BORDERED, null, null);
            } else if (Semaphore.RED.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.RED_BORDERED, null, null);
            } else if (Semaphore.BLACK.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.BLACK_BORDERED, null, null);
            }

        } else if (dataField instanceof DecodedDataField) {
            DecodedDataField<?> comboBox = (DecodedDataField<?>) dataField;
            setCellValue(row, cellindex, comboBox.getDecodedText());
            setCellStyle(row, cellindex, getStyle(BaseExcelContainer.WHITE_BORDERED, null, null));

        } else {
            setCellValue(row, cellindex, dataField.getValue());
            setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, BaseExcelType.Factory.fromDataType(dataField.getDataType()));
        }
    }

    protected int addSheetTitle(String title, int lastColumnIndex, Locale locale) throws FrameworkException {
        if (!BaseFunction.isBlank(title)) {
            // Titolo
            setCellValue(0, 0, title);
            setCellStyle(0, 0, null, BaseExcelFont.TITLE, null);
        }

        // Data estrazione
        setCellValue(1, 0, "Estratto il " + DataTypes.DATE.formatText(TimeStampUtil.sysdate(), locale) + " alle " + DataTypes.TIME.formatText(TimeStampUtil.sysdate(), locale));
        setCellStyle(1, 0, null, BaseExcelFont.SUB_TITLE, null);

        return 2;
    }

    public void setCellTotalizeStyle(int row, int cellindex, DataField<?> dataField) {
        if (dataField instanceof TextTotalizer || dataField instanceof InputTotalizer) {
            setCellStyle(row, cellindex, BaseExcelContainer.WHITE, null, BaseExcelType.Factory.fromDataType(dataField.getDataType()));
        }
    }

    /**
     * Aggiungeuna Grid al foglio excel
     *
     * @param grid
     */
    public void addGrid(boolean title, Grid<?> grid) throws FrameworkException {
        addSheet(BaseFunction.nvl(grid.getTitle(), grid.getName()), false);

        getSheet().setMargin(Sheet.FooterMargin, 0.25);
        getSheet().getFooter().setCenter("Pag. " + HeaderFooter.page() + " di " + HeaderFooter.numPages());

        // Titolo Foglio
        if (title) {
            rowIndex = addSheetTitle(BaseFunction.nvl(grid.getTitle(), grid.getName()), grid.getElements().size() - 1, grid.getLocale());
            rowIndex++;
        }

        // Titolo Griglia
        rowIndex = addGridTitle(rowIndex, grid);
        rowIndex++;

        getSheet().setRepeatingRows(CellRangeAddress.valueOf("1:" + rowIndex));
        getSheet().createFreezePane(0, rowIndex);

        // Dati Griglia
        rowIndex = addGridData(rowIndex, grid);
    }

    protected int addGridTitle(int rowIndex, Grid<?> grid) {
        // Titoli di colonna
        int cellIndex = 0;
        for (SimpleField field : grid.getElements()) {
            if (field instanceof DataField) {
                DataField<?> dataField = (DataField<?>) field;
                setCellValue(rowIndex, cellIndex, dataField.getDescription());
                setCellStyle(rowIndex, cellIndex, BaseExcelContainer.DARK_BLUE_BORDERED, BaseExcelFont.HEADER, null);
                cellIndex++;
            }
        }

        return rowIndex;
    }

    protected int addGridData(int rowIndex, Grid<?> grid) throws FrameworkException {
        int cellIndex = 0;

        // Dati
        DataTable<?> dataTable = grid.getDataSource();
        for (DataRow dataRow : dataTable) {
            cellIndex = 0;
            for (SimpleField field : grid) {
                SimpleField appField;
                try {
                    appField = (SimpleField) field.clone();
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }

                if (appField instanceof DataField) {
                    DataField<?> dataField = (DataField<?>) appField;
                    dataField.copyFromDataSource(dataRow);

                    setCell(rowIndex, cellIndex++, dataField);
                }
            }
            rowIndex++;
        }

        // Totali
        cellIndex = 0;
        for (SimpleField field : grid) {
            SimpleField appField;
            try {
                appField = (SimpleField) field.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }

            if (appField instanceof DataField) {
                if (appField instanceof TextTotalizer || appField instanceof InputTotalizer) {
                    DataField<?> dataField = (DataField<?>) appField;

                    String columnLetter = CellReference.convertNumToColString(cellIndex);

                    setCellTotalizeStyle(rowIndex, cellIndex, dataField);
                    setCellFormula(rowIndex, cellIndex, "sum(" + columnLetter + "1:" + columnLetter + rowIndex + ")");
                }

                cellIndex++;
            }
        }

        // Nascondo le colonne Hidden
        int i = 0;
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof InputField && ((InputField<?>) simpleField).isHidden()) {
                // setColumnsWidth(i, i, 0);
            } else {
                getSheet().autoSizeColumn(i);
            }

            i++;
        }

        return rowIndex;
    }

}
