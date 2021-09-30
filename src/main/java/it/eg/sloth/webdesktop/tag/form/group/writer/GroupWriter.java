package it.eg.sloth.webdesktop.tag.form.group.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.math.BigDecimal;
import java.text.MessageFormat;

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
 *
 * @author Enrico Grillini
 */
public class GroupWriter extends HtmlWriter {

    static final String ROW_OPEN = "<div class=\"row form-group mb-2\">";
    static final String ROW_CLOSE = "</div>";

    static final String CELL_OPEN = "<div class=\"m-0 pl-1 pr-1 {0}\">";
    static final String CELL_CLOSE = "</div>";

    public static String openGroup(String legend) {
        return new StringBuilder()
                .append("<fieldset>")
                .append(getElement("legend", !BaseFunction.isBlank(legend), legend))
                .toString();
    }

    public static String closeGroup() {
        return new StringBuilder()
                .append("</fieldset>")
                .toString();
    }

    public static String openRow() {
        return ROW_OPEN;
    }

    public static String closeRow() {
        return ROW_CLOSE;
    }

    public static String openCell(String width) throws FrameworkException {
        // Cell class
        String widthClass = "col-2";
        if (!BaseFunction.isBlank(width) && width.indexOf("cols") >= 0) {
            BigDecimal bigDecimal = BigDecimalUtil.parseBigDecimal(width.replace("cols", ""));
            widthClass = "col-" + bigDecimal.intValue();
        }

        return MessageFormat.format(CELL_OPEN, widthClass);
    }

    public static String closeCell() {
        return CELL_CLOSE;
    }

}
