package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.Semaphore;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelType;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public class GridXlsxWriter extends BaseXlsxWriter {

    protected int rowIndex;

    public GridXlsxWriter() {
        super();
        rowIndex = 0;
    }

    public GridXlsxWriter(boolean title, Grid<?>... grids) throws FrameworkException {
        super();

        for (Grid<?> grid : grids) {
            addGrid(title, grid);
        }
    }

    public void setCell(int row, int cellindex, DataField<?> dataField) throws FrameworkException {
        if (dataField instanceof Semaphore) {
            Semaphore semaforo = (Semaphore) dataField;

            setCellValue(row, cellindex, semaforo.getDecodedText());
            if (Semaphore.WHITE.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, null, null);
            } else if (Semaphore.GREEN.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.GREEN_BORDERED, null, null, null);
            } else if (Semaphore.YELLOW.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.YELLOW_BORDERED, null, null, null);
            } else if (Semaphore.RED.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.RED_BORDERED, null, null, null);
            } else if (Semaphore.BLACK.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.BLACK_BORDERED, null, null, null);
            }

        } else if (dataField instanceof DecodedDataField) {
            DecodedDataField<?> comboBox = (DecodedDataField<?>) dataField;
            setCellValue(row, cellindex, comboBox.getDecodedText());
            setCellStyle(row, cellindex, getStyle(BaseExcelContainer.WHITE_BORDERED, null, null, null));

        } else {
            setCellValue(row, cellindex, dataField.getValue());
            setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, BaseExcelType.Factory.fromDataType(dataField.getDataType()), null);
        }
    }

    protected int addSheetTitle(String title, String subTitle, Locale locale, int size) throws FrameworkException {
        int row = 0;

        // Titolo
        if (!BaseFunction.isBlank(title)) {
            // Titolo
            setCellValue(row, 0, title);
            setCellStyle(row, 0, null, BaseExcelFont.TITLE, null, null);
            getRow(row).setHeight((short) 450);
            addMergedRegion(row, 0, row, size - 1);
            row++;
        }

        // Sotto titolo
        if (!BaseFunction.isBlank(subTitle)) {
            setCellValue(row, 0, subTitle);
            setCellStyle(row, 0, null, BaseExcelFont.SUB_TITLE, null, null);
            getRow(row).setHeight((short) 400);
            addMergedRegion(row, 0, row, size - 1);
            row++;
        }

        // Data estrazione
        setCellValue(row, 0, "Estratto il " + DataTypes.DATE.formatText(TimeStampUtil.sysdate(), locale) + " alle " + DataTypes.TIME.formatText(TimeStampUtil.sysdate(), locale));
        setCellStyle(row, 0, null, BaseExcelFont.COMMENT, null, null);
        addMergedRegion(row, 0, row, size - 1);
        row++;

        return row;
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
            rowIndex = addSheetTitle(grid.getTitle(), grid.getDescription(), grid.getLocale(), grid.getElements().size());
            rowIndex++;
        }

        // Intestazioni di colonna
        rowIndex = addGridHeader(rowIndex, grid);
        rowIndex++;

        getSheet().setRepeatingRows(CellRangeAddress.valueOf("1:" + rowIndex));
        getSheet().createFreezePane(0, rowIndex);

        // Dati Griglia
        rowIndex = addGridData(rowIndex, grid);
    }

    protected int addGridHeader(int rowIndex, Grid<?> grid) {
        // Intestazioni di colonna
        int cellIndex = 0;
        for (SimpleField field : grid.getElements()) {
            if (field instanceof DataField) {
                DataField<?> dataField = (DataField<?>) field;
                setCellValue(rowIndex, cellIndex, dataField.getDescription());
                setCellStyle(rowIndex, cellIndex, BaseExcelContainer.DARK_BLUE_BORDERED, BaseExcelFont.HEADER, null, null);
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
                SimpleField appField = field.newInstance();

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
            if (field instanceof DataField) {
                if (field.getFieldType() == FieldType.TEXT_TOTALIZER || field.getFieldType() == FieldType.INPUT_TOTALIZER) {
                    DataField<?> dataField = (DataField<?>) field;

                    String columnLetter = CellReference.convertNumToColString(cellIndex);
                    setCellStyle(rowIndex, cellIndex, null, BaseExcelFont.HIGHIGHTED, BaseExcelType.Factory.fromDataType(dataField.getDataType()), null);
                    setCellFormula(rowIndex, cellIndex, "sum(" + columnLetter + "1:" + columnLetter + rowIndex + ")");
                }

                cellIndex++;
            }
        }

        // Nascondo le colonne Hidden
        int i = 0;
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof InputField && ((InputField<?>) simpleField).isHidden()) {
                setColumnsWidth(i, i, 0);
            } else {
                getSheet().autoSizeColumn(i);
            }

            i++;
        }

        return rowIndex;
    }

}
