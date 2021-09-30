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
public class BaseExcelContainer {

    public static final BaseExcelContainer WHITE = new BaseExcelContainer(Integer.valueOf(IndexedColors.WHITE.getIndex()), null, null, null, null);
    public static final BaseExcelContainer WHITE_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.WHITE.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
    public static final BaseExcelContainer WHITE_BORDERED_BOTTOM = new BaseExcelContainer(Integer.valueOf(IndexedColors.WHITE.getIndex()), null, null, BorderStyle.THIN, null);
    public static final BaseExcelContainer DARK_BLUE = new BaseExcelContainer(Integer.valueOf(IndexedColors.ROYAL_BLUE.getIndex()), null, null, null, null);
    public static final BaseExcelContainer DARK_BLUE_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.ROYAL_BLUE.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
    public static final BaseExcelContainer GRAY = new BaseExcelContainer(Integer.valueOf(IndexedColors.GREY_50_PERCENT.getIndex()), null, null, null, null);
    public static final BaseExcelContainer GRAY_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.GREY_50_PERCENT.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
    public static final BaseExcelContainer LIGHT_GRAY = new BaseExcelContainer(Integer.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()), null, null, null, null);
    public static final BaseExcelContainer LIGHT_GRAY_BORDERED = new BaseExcelContainer(Integer.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()), BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);

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
