package it.eg.sloth.framework.utility.xlsx;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class ExcelUtil {

    private ExcelUtil() {
        // NOP
    }

    /**
     * Ritorna la riga indicata. Se non esiste la crea.
     *
     * @param sheet
     * @param rowIndex
     * @return
     */
    public static Row getRow(Sheet sheet, int rowIndex) {
        Row row = sheet.getRow(rowIndex);
        if (sheet.getRow(rowIndex) == null) {
            row = sheet.createRow(rowIndex);
        }

        return row;
    }


    /**
     * Ritorna la cella indicata. Se non esiste la crea.
     *
     * @param sheet
     * @param rowIndex
     * @param cellIndex
     * @return
     */
    public static Cell getCell(Sheet sheet, int rowIndex, int cellIndex) {
        Row row = getRow(sheet, rowIndex);

        Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            cell = row.createCell(cellIndex);
        }

        return cell;
    }

    /**
     * Ritorna il valore contenuto in una cella
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        } else if (cell.getCellType() == CellType.STRING || (cell.getCellType() == CellType.FORMULA && cell.getCachedFormulaResultType() == CellType.STRING)) {
            return StringUtil.parseString(cell.getRichStringCellValue().getString());
        } else if ("@".equals(cell.getCellStyle().getDataFormatString())) {
            double value = cell.getNumericCellValue();
            if (value == 0) {
                return StringUtil.EMPTY;
            } else if (Math.floor(value) == value) {
                return "" + (int) value;
            } else {
                return "" + value;
            }
        } else if (cell.getCellType() == CellType.NUMERIC || (cell.getCellType() == CellType.FORMULA && cell.getCachedFormulaResultType() == CellType.NUMERIC)) {
            if (DateUtil.isCellDateFormatted(cell)) {
                return TimeStampUtil.toTimestamp(cell.getDateCellValue());
            } else {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.FORMULA && cell.getCachedFormulaResultType() == CellType.ERROR) {
            return null;
        } else {
            return null;
        }
    }

    /**
     * Imposta il valore nella cella.
     *
     * @param sheet
     * @param rowIndex
     * @param cellIndex
     * @param value
     */
    public static void setCellValue(XSSFSheet sheet, int rowIndex, int cellIndex, Object value) {
        Cell cell = getCell(sheet, rowIndex, cellIndex);

        if (value == null)
            return;

        if (value instanceof String) {
            cell.setCellValue(new XSSFRichTextString((String) value));
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if (value instanceof Timestamp) {
            cell.setCellValue(TimeStampUtil.toCalendar((Timestamp) value));
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
    }

    /**
     * Imposta lo stile della cella
     *
     * @param sheet
     * @param rowIndex
     * @param cellIndex
     * @param cellStyle
     */
    public static void setCellStyle(XSSFSheet sheet, int rowIndex, int cellIndex, CellStyle cellStyle) {
        Cell cell = getCell(sheet, rowIndex, cellIndex);
        cell.setCellStyle(cellStyle);
    }


    /**
     * Imposta lo stile di un range
     *
     * @param sheet
     * @param rowIndex1
     * @param cellIndex1
     * @param rowIndex2
     * @param cellIndex2
     * @param cellStyle
     */
    public static void setRangeStyle(XSSFSheet sheet, int rowIndex1, int cellIndex1, int rowIndex2, int cellIndex2, CellStyle cellStyle) {
        for (int i = rowIndex1; i <= rowIndex2; i++) {
            for (int j = cellIndex1; j <= cellIndex2; j++) {
                Cell cell = getCell(sheet, i, j);
                cell.setCellStyle(cellStyle);
            }
        }
    }

}
