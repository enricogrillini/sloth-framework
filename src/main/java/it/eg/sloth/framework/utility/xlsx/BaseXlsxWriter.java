package it.eg.sloth.framework.utility.xlsx;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelContainer;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelFont;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelStyle;
import it.eg.sloth.framework.utility.xlsx.style.BaseExcelType;
import lombok.Getter;

@Getter
public class BaseXlsxWriter {

  private XSSFWorkbook workbook;
  private XSSFSheet sheet;
  private XSSFDataFormat dataFormat;

  private Map<BaseExcelStyle, CellStyle> cellStyleMap;

  public BaseXlsxWriter(InputStream template) throws IOException {
    workbook = new XSSFWorkbook(template);
    sheet = workbook.getSheetAt(0);
    dataFormat = workbook.createDataFormat();

    cellStyleMap = new HashMap<BaseExcelStyle, CellStyle>();
  }

  public BaseXlsxWriter() {
    workbook = new XSSFWorkbook();
    dataFormat = workbook.createDataFormat();

    cellStyleMap = new HashMap<BaseExcelStyle, CellStyle>();
  }

  public XSSFSheet addSheet(String name, boolean landscape) {
    sheet = workbook.createSheet(name);

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

    return sheet;
  }

  public CellStyle getStyle(BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType) {
    BaseExcelStyle baseExcelStyle = new BaseExcelStyle(baseExcelContainer, baseExcelFont, baseExcelType);

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
  public XSSFRow getRow(int rowIndex) {
    XSSFRow row = sheet.getRow(rowIndex);
    if (sheet.getRow(rowIndex) == null) {
      row = sheet.createRow(rowIndex);
    }

    return row;
  }

  /**
   * Ritorna la cella indicata. Se non esiste la crea.
   * 
   * @param rowIndex
   * @param cellIndex
   * @return
   */
  public Cell getCell(int rowIndex, int cellIndex) {
    Row row = getRow(rowIndex);

    Cell cell = row.getCell(cellIndex);
    if (cell == null) {
      cell = row.createCell(cellIndex);
    }

    return cell;
  }

  /**
   * Imposta il valore nella cella.
   * 
   * @param rowIndex
   * @param cellIndex
   * @param value
   */
  public void setCellValue(int rowIndex, int cellIndex, Object value) {
    Cell cell = getCell(rowIndex, cellIndex);

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
   * Imposta il valore nella cella.
   * 
   * @param rowIndex
   * @param cellIndex
   * @param value
   */
  public void setCellFormula(int rowIndex, int cellIndex, String formula) {
    Cell cell = getCell(rowIndex, cellIndex);
    cell.setCellFormula(formula);
  }

  /**
   * Imposta lo stile della cella
   * 
   * @param rowIndex
   * @param cellIndex
   * @param cellStyle
   */
  public void setCellStyle(int rowIndex, int cellIndex, CellStyle cellStyle) {
    Cell cell = getCell(rowIndex, cellIndex);

    cell.setCellStyle(cellStyle);
  }

  /**
   * Imposta lo stile della cella
   * 
   * @param rowIndex
   * @param cellIndex
   * @param cellStyle
   */
  public void setCellStyle(int rowIndex, int cellIndex, BaseExcelContainer baseExcelContainer, BaseExcelFont baseExcelFont, BaseExcelType baseExcelType) {
    Cell cell = getCell(rowIndex, cellIndex);

    CellStyle cellStyle = getStyle(baseExcelContainer, baseExcelFont, baseExcelType);
    cell.setCellStyle(cellStyle);
  }

  // public void setCell(int rowIndex, int cellIndex, Object value, CellStyle
  // cellStyle) {
  // setCellValue(rowIndex, cellIndex, value);
  // setCellStyle(rowIndex, cellIndex, cellStyle);
  // }

}
