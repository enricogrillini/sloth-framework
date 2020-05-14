package it.eg.sloth.framework.utility.xlsx.style;

import org.apache.poi.ss.usermodel.Font;
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
public class BaseExcelFont {

  public static final BaseExcelFont TITLE = new BaseExcelFont(null, new Integer(16), new Integer(IndexedColors.DARK_BLUE.getIndex()), true, false);
  public static final BaseExcelFont SUB_TITLE = new BaseExcelFont(null, new Integer(10), new Integer(IndexedColors.GREY_80_PERCENT.getIndex()), false, true);
  public static final BaseExcelFont HEADER = new BaseExcelFont(null, null, new Integer(IndexedColors.WHITE.getIndex()), true, false);

  private String name;
  private Integer heightInPoints;
  private Integer color;
  private boolean bold;
  private boolean italic;

  BaseExcelFont(String name, Integer heightInPoints, Integer color, boolean bold, boolean italic) {
    this.name = name;
    this.heightInPoints = heightInPoints;
    this.color = color;
    this.bold = bold;
    this.italic = italic;
  }

  /**
   * Ritorna il Font POI sulla base delle info disponibili
   * 
   * @param workbook
   * @return
   */
  public Font fontFromWb(Workbook workbook) {
    Font result = workbook.createFont();

    if (getName() != null) {
      result.setFontName(getName());
    }

    if (getHeightInPoints() != null) {
      result.setFontHeightInPoints((short) getHeightInPoints().intValue());
    }

    if (getColor() != null) {
      result.setColor((short) getColor().intValue());
    }

    result.setBold(isBold());
    result.setItalic(isItalic());

    return result;
  }

}
