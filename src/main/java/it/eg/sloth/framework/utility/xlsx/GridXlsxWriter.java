package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.Hidden;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.form.fields.field.impl.Semaphore;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.pivot.*;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelType;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataField;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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


    public GridXlsxWriter() {
        super();
    }

    public GridXlsxWriter(XSSFWorkbook workbook) {
        setWorkbook(workbook);
    }

    public GridXlsxWriter(boolean title, Grid<?>... grids) throws FrameworkException {
        super();

        for (Grid<?> grid : grids) {
            addGrid(title, grid);
        }
    }

    protected void setCell(int row, int cellindex, DataField<?> dataField, DataRow dataRow) throws FrameworkException {
        if (dataField instanceof Semaphore) {
            Semaphore semaforo = (Semaphore) dataField.newInstance();
            semaforo.copyFromDataSource(dataRow);

            if (Semaphore.WHITE.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, null, null, true);
            } else if (Semaphore.GREEN.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.GREEN_BORDERED, null, null, null, true);
            } else if (Semaphore.YELLOW.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.YELLOW_BORDERED, null, null, null, true);
            } else if (Semaphore.RED.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.RED_BORDERED, null, null, null, true);
            } else if (Semaphore.BLACK.equals(semaforo.getValue())) {
                setCellStyle(row, cellindex, BaseExcelContainer.BLACK_BORDERED, null, null, null, true);
            }
            setCellValue(row, cellindex, semaforo.getDecodedText());

        } else if (dataField instanceof DecodedDataField) {
            DecodedDataField<?> comboBox = (DecodedDataField<?>) dataField.newInstance();
            comboBox.copyFromDataSource(dataRow);

            setCellStyle(row, cellindex, getStyle(BaseExcelContainer.WHITE_BORDERED, null, null, null, true));
            setCellValue(row, cellindex, comboBox.getDecodedText());

        } else if (dataField instanceof MultipleAutoComplete) {
            MultipleAutoComplete<?> multipleAutoComplete = (MultipleAutoComplete<?>) dataField.newInstance();
            multipleAutoComplete.copyFromDataSource(dataRow);

            setCellStyle(row, cellindex, getStyle(BaseExcelContainer.WHITE_BORDERED, null, null, null, true));
            setCellValue(row, cellindex, multipleAutoComplete.getText());
        } else {
            setCellStyle(row, cellindex, BaseExcelContainer.WHITE_BORDERED, null, BaseExcelType.Factory.fromDataType(dataField.getDataType()), null, true);
            setCellValue(row, cellindex, dataRow.getObject(dataField.getAlias()));
        }
    }

    /**
     * Aggiungeuna Grid al foglio excel
     *
     * @param grid
     */
    public void addGrid(boolean title, Grid<?> grid) throws FrameworkException {
        int rowIndex = 0;

        addSheet(BaseFunction.nvl(grid.getTitle(), grid.getName()), false);

        getSheet().setMargin(Sheet.FooterMargin, 0.25);
        getSheet().getFooter().setCenter("Pag. " + HeaderFooter.page() + " di " + HeaderFooter.numPages());

        // Titolo Foglio
        if (title) {
            rowIndex = addGridTitle(rowIndex, grid);
            rowIndex++;
        }

        // Intestazioni di colonna
        int topRow = rowIndex;
        rowIndex = addGridHeader(rowIndex, grid);

        getSheet().setRepeatingRows(CellRangeAddress.valueOf("1:" + rowIndex));
        getSheet().createFreezePane(0, rowIndex);

        // Dati Griglia
        rowIndex = addGridData(rowIndex, grid);

        // Totali Griglia
        int bottomRow = rowIndex - 1;
        addGridTotal(rowIndex, grid);

        // Gestione colonne nascoste
        autoSizeColumn(grid);

        // Aggiunge le Pivot
        CellReference topLeft = new CellReference(topRow, 0);
        CellReference bottomRight = new CellReference(bottomRow, getColumnCount(grid) - 1);
        addPivots(title, grid, topLeft, bottomRight);
    }


    protected int addGridTitle(int rowIndex, Grid<?> grid) throws FrameworkException {
        return addSheetTitle(rowIndex, grid.getTitle(), grid.getDescription(), grid.getLocale(), getColumnCount(grid));
    }

    protected int addGridHeader(int rowIndex, Grid<?> grid) {
        // Intestazioni di colonna
        int cellIndex = 0;
        for (SimpleField field : grid.getElements()) {
            if (field instanceof Hidden) {
                continue;
            }

            if (field instanceof DataField) {
                DataField<?> dataField = (DataField<?>) field;
                setCellStyle(rowIndex, cellIndex, BaseExcelContainer.DARK_BLUE_BORDERED, BaseExcelFont.HEADER, null, null, true);
                setCellValue(rowIndex, cellIndex, dataField.getDescription());
                cellIndex++;
            }
        }

        rowIndex++;

        return rowIndex;
    }

    protected int addGridData(int rowIndex, Grid<?> grid) throws FrameworkException {
        int cellIndex = 0;

        DataTable<?> dataTable = grid.getDataSource();
        for (DataRow dataRow : dataTable) {
            cellIndex = 0;
            for (SimpleField field : grid) {
                if (field instanceof Hidden) {
                    continue;
                }

                SimpleField appField = field.newInstance();
                if (appField instanceof DataField) {
                    DataField<?> dataField = (DataField<?>) appField;
                    setCell(rowIndex, cellIndex++, dataField, dataRow);
                }
            }
            rowIndex++;
        }

        return rowIndex;
    }

    protected int addGridTotal(int rowIndex, Grid<?> grid) {
        int cellIndex = 0;
        for (SimpleField field : grid) {
            if (field instanceof Hidden) {
                continue;
            }

            if (field instanceof DataField) {
                if (field.getFieldType() == FieldType.TEXT_TOTALIZER || field.getFieldType() == FieldType.INPUT_TOTALIZER) {
                    DataField<?> dataField = (DataField<?>) field;

                    String columnLetter = CellReference.convertNumToColString(cellIndex);
                    setCellStyle(rowIndex, cellIndex, null, BaseExcelFont.HIGHIGHTED, BaseExcelType.Factory.fromDataType(dataField.getDataType()), null, true);
                    setCellFormula(rowIndex, cellIndex, "sum(" + columnLetter + "1:" + columnLetter + rowIndex + ")");
                }

                cellIndex++;
            }
        }

        rowIndex++;

        return rowIndex;
    }

    /**
     * Nascondo le colonne Hidden
     *
     * @param grid
     */
    protected int getColumnCount(Grid<?> grid) {
        int i = 0;
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof Hidden) {
                continue;
            }
            i++;
        }

        return i;
    }

    /**
     * Nascondo le colonne Hidden
     *
     * @param grid
     */
    protected void autoSizeColumn(Grid<?> grid) {
        int i = 0;
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof Hidden) {
                continue;
            }

            if (simpleField instanceof InputField && ((InputField<?>) simpleField).isHidden()) {
                setColumnsWidth(i, i, 0);
            } else {
                getSheet().autoSizeColumn(i);
            }

            i++;
        }
    }


    /**
     * Aggiunge le pivot al foglio excel
     *
     * @param grid
     */
    public void addPivots(boolean title, Grid<?> grid, CellReference topLeft, CellReference bottomRight) throws FrameworkException {
        XSSFSheet dataSheet = getSheet();
        if (grid.getPivots() != null) {
            for (Pivot pivot : grid.getPivots()) {
                int rowIndex = 0;

                // Aggiungo il foglio per la Pivot
                addSheet(BaseFunction.nvl(pivot.getTitle(), pivot.getName()), false);

                // Titolo Foglio
                if (title) {
                    rowIndex = addSheetTitle(rowIndex, pivot.getTitle(), null, grid.getLocale(), pivot.getElements().size());
                    rowIndex++;
                }

                addPivot(grid, pivot, rowIndex, dataSheet, new AreaReference(topLeft, bottomRight, SpreadsheetVersion.EXCEL2007));

                getSheet().setMargin(Sheet.FooterMargin, 0.25);
                getSheet().getFooter().setCenter("Pag. " + HeaderFooter.page() + " di " + HeaderFooter.numPages());
            }
        }

    }

    public void addPivot(Grid<?> grid, Pivot pivot, int rowIndex, Sheet dataSource, AreaReference areaReference) throws FrameworkException {
        // Aggiungo la Pivot
        XSSFPivotTable pivotTable = getSheet().createPivotTable(areaReference, new CellReference(rowIndex + 2, 0),
                dataSource);

        // Aggiungo i campi della pivot
        DataFormat df = getWorkbook().createDataFormat();
        int valueCount = 0;
        for (PivotElement pivotElement : pivot.getElements()) {
            int col = getPivotIndex(grid, pivotElement);

            // Informazioni sul formato
            SimpleField field = grid.getElement(pivotElement.getFieldAlias());
            BaseExcelType baseExcelType = null;
            if (field instanceof DataField) {
                DataField<?> dataField = (DataField) field;
                baseExcelType = BaseExcelType.Factory.fromDataType(dataField.getDataType());
            }

            if (pivotElement instanceof PivotFilter) {
                pivotTable.addReportFilter(col);
            } else if (pivotElement instanceof PivotRow) {
                pivotTable.addRowLabel(col);
            } else if (pivotElement instanceof PivotColumn) {
                if (baseExcelType != null) {
                    pivotTable.addColLabel(col, baseExcelType.getFormat());
                } else {
                    pivotTable.addColLabel(col);
                }

            } else if (pivotElement instanceof PivotValue) {
                PivotValue pivotValue = (PivotValue) pivotElement;
                pivotTable.addColumnLabel(DataConsolidateFunction.valueOf(pivotValue.getConsolidateFunction().value()), col);

                if (baseExcelType != null) {
                    CTDataField ctDataField = pivotTable.getCTPivotTableDefinition().getDataFields().getDataFieldArray(valueCount);
                    ctDataField.setName(BaseFunction.isBlank(pivotElement.getDescription()) ? field.getDescription() : pivotElement.getDescription());
                    ctDataField.setNumFmtId(df.getFormat(baseExcelType.getFormat()));
                }
                valueCount++;
            }


        }
    }


    private int getPivotIndex(Grid<?> grid, PivotElement pivotElement) throws FrameworkException {
        int i = 0;
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof Hidden) {
                continue;
            }

            if (simpleField.getName().equalsIgnoreCase(pivotElement.getFieldAlias())) {
                return i;
            }
            i++;
        }

        throw new FrameworkException(ExceptionCode.PIVOT_ELEMENT_NOT_FOUND, pivotElement.getFieldAlias());
    }

}
