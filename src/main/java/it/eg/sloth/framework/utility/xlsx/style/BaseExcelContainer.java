package it.eg.sloth.framework.utility.xlsx.style;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BaseExcelContainer {

  public static final BaseExcelContainer WHITE = new BaseExcelContainer(Integer.valueOf(IndexedColors.WHITE.getIndex()), null, null, null, null);
  public static final BaseExcelContainer WHITE_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.WHITE.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
  public static final BaseExcelContainer DARK_BLUE = new BaseExcelContainer(Integer.valueOf(IndexedColors.DARK_BLUE.getIndex()), null, null, null, null);
  public static final BaseExcelContainer DARK_BLUE_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.DARK_BLUE.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);

  public static final BaseExcelContainer GREEN_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.GREEN.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
  public static final BaseExcelContainer YELLOW_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.YELLOW.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
  public static final BaseExcelContainer RED_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.RED.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
  public static final BaseExcelContainer BLACK_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.BLACK.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);

  private Integer color;
  private BorderStyle borderTop;
  private BorderStyle borderRight;
  private BorderStyle borderBottom;
  private BorderStyle borderLeft;

  public BaseExcelContainer(Integer color, BorderStyle borderTop, BorderStyle borderRight, BorderStyle borderBottom, BorderStyle borderLeft) {
    super();
    this.color = color;
    this.borderTop = borderTop;
    this.borderRight = borderRight;
    this.borderBottom = borderBottom;
    this.borderLeft = borderLeft;
  }

  /**
   * Ritorna il CellStyle POI sulla base delle info disponibili
   * 
   * @param workbook
   * @return
   */
  public CellStyle cellStyleFromWb(Workbook workbook) {
    CellStyle result = workbook.createCellStyle();

    if (getColor() != null) {
      result.setFillForegroundColor((short) getColor().intValue());
      result.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    if (getBorderTop() != null) {
      result.setBorderTop(getBorderTop());
    }

    if (getBorderRight() != null) {
      result.setBorderRight(getBorderRight());
    }

    if (getBorderBottom() != null) {
      result.setBorderBottom(getBorderBottom());
    }

    if (getBorderLeft() != null) {
      result.setBorderLeft(getBorderLeft());
    }

    return result;
  }

}
