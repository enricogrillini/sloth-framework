package it.eg.sloth.framework.utility.xlsx.style;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Setter
@ToString
@EqualsAndHashCode
public class BaseExcelFont {

    public static final BaseExcelFont TITLE = new BaseExcelFont(null, Integer.valueOf(16), Integer.valueOf(IndexedColors.DARK_BLUE.getIndex()), true, false);
    public static final BaseExcelFont TITLE2 = new BaseExcelFont(null, Integer.valueOf(14), Integer.valueOf(IndexedColors.DARK_BLUE.getIndex()), true, false);
    public static final BaseExcelFont SUB_TITLE = new BaseExcelFont(null, Integer.valueOf(13), Integer.valueOf(IndexedColors.DARK_BLUE.getIndex()), false, true);
    public static final BaseExcelFont COMMENT = new BaseExcelFont(null, Integer.valueOf(10), Integer.valueOf(IndexedColors.GREY_80_PERCENT.getIndex()), false, true);
    public static final BaseExcelFont HEADER = new BaseExcelFont(null, null, Integer.valueOf(IndexedColors.WHITE.getIndex()), true, false);
    public static final BaseExcelFont HIGHIGHTED = new BaseExcelFont(null, null, null, true, false);

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
