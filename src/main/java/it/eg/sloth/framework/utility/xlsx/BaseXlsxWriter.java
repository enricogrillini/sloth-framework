package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelStyle;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelType;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
@Getter
public class BaseXlsxWriter implements Closeable {

    @Setter
    protected XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFDataFormat dataFormat;

    private Map<BaseExcelStyle, CellStyle> cellStyleMap;

    public BaseXlsxWriter(InputStream template) throws IOException {
        workbook = new XSSFWorkbook(template); // Forza il ricalcolo delle formule
        workbook.setForceFormulaRecalculation(true);

        sheet = workbook.getSheetAt(0);
        dataFormat = workbook.createDataFormat();

        cellStyleMap = new HashMap<>();
    }

    public BaseXlsxWriter() {
        workbook = new XSSFWorkbook();
        workbook.setForceFormulaRecalculation(true); // Forza il ricalcolo delle formule

        dataFormat = workbook.createDataFormat();
        cellStyleMap = new HashMap<>();
    }

    public XSSFSheet addSheet(String name, boolean landscape) {
        sheet = workbook.createSheet(StringUtil.toFileName(name));

        // Imposto i margini
        sheet.setMargin(Sheet.LeftMargin, 0.25);
        sheet.setMargin(Sheet.RightMargin, 0.25);
        sheet.setMargin(Sheet.TopMargin, 0.5);
        sheet.setMargin(Sheet.BottomMargin, 0.5);

        sheet.setAutobreaks(true);

        // Definisco le impostazioni di stampa di default
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setFitHeight((short) 0);
        printSetup.setFitWidth((short) 1);
        printSetup.setPaperSize(PrintSetup.A4_PAPERSIZE);
        printSetup.setLandscape(landscape);

        sheet.setFitToPage(true);

        return sheet;
    }

    public XSSFSheet getSheet(String name) {
        sheet = workbook.getSheet(name);
        return getSheet();
    }

    public XSSFSheet getSheet(int index) {
        sheet = workbook.getSheetAt(index);
        return getSheet();
    }

    public int addSheetTitle(int rowIndex, String title, String subTitle, Locale locale, int size) throws FrameworkException {
        // Titolo
        if (!BaseFunction.isBlank(title)) {
            // Titolo
            setCellValue(rowIndex, 0, title);
            setCellStyle(rowIndex, 0, null, BaseExcelFont.TITLE, null, null, false);
            getRow(rowIndex).setHeight((short) 450);
            rowIndex++;
        }

        // Sotto titolo
        if (!BaseFunction.isBlank(subTitle)) {
            setCellValue(rowIndex, 0, subTitle);
            setCellStyle(rowIndex, 0, null, BaseExcelFont.SUB_TITLE, null, null, false);
            getRow(rowIndex).setHeight((short) 400);
            rowIndex++;
        }

        // Data estrazione
        setCellValue(rowIndex, 0, "Estratto il " + DataTypes.DATE.formatText(TimeStampUtil.sysdate(), locale) + " alle " + DataTypes.TIME.formatText(TimeStampUtil.sysdate(), locale));
        setCellStyle(rowIndex, 0, null, BaseExcelFont.COMMENT, null, null, false);
        addMergedRegion(rowIndex, 0, rowIndex, size - 1);
        rowIndex++;

        return rowIndex;
    }

    public CellStyle getStyle(BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType, HorizontalAlignment horizontalAlignment) {
        return getStyle(baseExcelContainer, baseExcelFont, baseExcelType, horizontalAlignment, true);
    }

    public CellStyle getStyle(BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType, HorizontalAlignment horizontalAlignment, boolean wrap) {
        BaseExcelStyle baseExcelStyle = new BaseExcelStyle(baseExcelContainer, baseExcelFont, baseExcelType, horizontalAlignment, wrap);

        CellStyle result;
        if (cellStyleMap.containsKey(baseExcelStyle)) {
            result = cellStyleMap.get(baseExcelStyle);
        } else {
            // Definisco lo stile di riferimento
            if (baseExcelContainer != null) {
                result = baseExcelContainer.cellStyleFromWb(workbook);
            } else {
                result = getWorkbook().createCellStyle();
            }

            // Se specificato, imposto il font
            if (baseExcelFont != null) {
                result.setFont(baseExcelFont.fontFromWb(workbook));
            }

            // Se specificato, imposto il formato dati
            if (baseExcelType != null) {
                result.setDataFormat(baseExcelType.formatFromDataFormat(dataFormat));
            }

            //  Se specificato, imposto l'allineamento orrizontale
            if (horizontalAlignment != null) {
                result.setAlignment(horizontalAlignment);
            }

            result.setWrapText(wrap);
            result.setVerticalAlignment(VerticalAlignment.CENTER);

            cellStyleMap.put(baseExcelStyle, result);
        }

        return result;
    }

    /**
     * Ritorna la riga indicata. Se non esiste la crea.
     *
     * @param rowIndex
     * @return
     */
    public Row getRow(int rowIndex) {
        return ExcelUtil.getRow(getSheet(), rowIndex);
    }

    /**
     * Ritorna la cella indicata. Se non esiste la crea.
     *
     * @param rowIndex
     * @param cellIndex
     * @return
     */
    public Cell getCell(int rowIndex, int cellIndex) {
        return ExcelUtil.getCell(getSheet(), rowIndex, cellIndex);
    }

    /**
     * Imposta la larghezza delle colonne nel range di colonne specificato
     *
     * @param columnIndex1
     * @param columnIndex2
     * @param width
     */
    public void setColumnsWidth(int columnIndex1, int columnIndex2, int width) {
        for (int i = columnIndex1; i <= columnIndex2; i++) {
            getSheet().setColumnWidth(i, width);
        }
    }

    /**
     * Adatta la larghezza delle colonne indicate
     *
     * @param columnIndex1
     * @param columnIndex2
     */
    public void autoSizeColumns(int columnIndex1, int columnIndex2) {
        for (int i = columnIndex1; i <= columnIndex2; i++) {
            getSheet().autoSizeColumn(i);
        }
    }

    /**
     * Imposta il valore nella cella.
     *
     * @param rowIndex
     * @param cellIndex
     * @param value
     */
    public Cell setCellValue(int rowIndex, int cellIndex, Object value) {
        return ExcelUtil.setCellValue(getSheet(), rowIndex, cellIndex, value);
    }

    public void addMergedRegion(int rowIndex1, int columnIndex1, int rowIndex2, int columnIndex2) {
        if (rowIndex1 != rowIndex2 || columnIndex1 != columnIndex2) {
            getSheet().addMergedRegion(new CellRangeAddress(rowIndex1, rowIndex2, columnIndex1, columnIndex2));
        }
    }

    /**
     * Imposta il valore nella cella.
     *
     * @param rowIndex
     * @param cellIndex
     * @param formula
     */
    public Cell setCellFormula(int rowIndex, int cellIndex, String formula) {
        return ExcelUtil.setCellFormula(getSheet(), rowIndex, cellIndex, formula);
    }

    /**
     * Imposta lo stile della cella
     *
     * @param rowIndex
     * @param cellIndex
     * @param cellStyle
     */
    public Cell setCellStyle(int rowIndex, int cellIndex, CellStyle cellStyle) {
        return ExcelUtil.setCellStyle(getSheet(), rowIndex, cellIndex, cellStyle);
    }
    
    /**
     * Imposta il valore e lo stile nella cella.
     *
     * @param rowIndex
     * @param cellIndex
     * @param value
     * @param cellStyle
     */
    public Cell setCellValueAndStyle(int rowIndex, int cellIndex, Object value, CellStyle cellStyle) {
        setCellValue(rowIndex, cellIndex, value);
        return setCellStyle(rowIndex, cellIndex, cellStyle);
    }

    /**
     * Imposta lo stile di un range
     *
     * @param rowIndex1
     * @param cellIndex1
     * @param rowIndex2
     * @param cellIndex2
     * @param cellStyle
     */
    public void setRangeStyle(int rowIndex1, int cellIndex1, int rowIndex2, int cellIndex2, CellStyle cellStyle) {
        ExcelUtil.setRangeStyle(getSheet(), rowIndex1, cellIndex1, rowIndex2, cellIndex2, cellStyle);
    }

    public void setCellStyle(int rowIndex, int cellIndex, BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType, HorizontalAlignment horizontalAlignment) {
        setCellStyle(rowIndex, cellIndex, baseExcelContainer, baseExcelFont, baseExcelType, horizontalAlignment, true);
    }

    // Imposta lo stile della cella
    public void setCellStyle(int rowIndex, int cellIndex, BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType, HorizontalAlignment horizontalAlignment, boolean wrap) {
        Cell cell = getCell(rowIndex, cellIndex);

        CellStyle cellStyle = getStyle(baseExcelContainer, baseExcelFont, baseExcelType, horizontalAlignment, wrap);
        cell.setCellStyle(cellStyle);
    }

    public void write(OutputStream outputStream) throws IOException {
        workbook.write(outputStream);
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }
}
