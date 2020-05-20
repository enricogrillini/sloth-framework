package it.eg.sloth.webdesktop.tag.form.group.writer;

import java.math.BigDecimal;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

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
 *
 * @author Enrico Grillini
 */
public class GroupWriter extends AbstractHtmlWriter {

    static final String BASE_CELL_CLASS = "col-";
    static final String ROW_CLASS = "row form-group";

    public static String openGroup(String legend) {
        return new StringBuilder()
                .append("<filedset>")
                .append(getElement("legend", !BaseFunction.isBlank(legend), legend))
                .toString();
    }

    public static String closeGroup() {
        return new StringBuilder()
                .append("</filedset>")
                .toString();
    }

    public static String openRow() {
        return new StringBuilder()
                .append("<div")
                .append(getAttribute("class", ROW_CLASS))
                .append(">")
                .toString();
    }

    public static String closeRow() {
        return new StringBuilder()
                .append("</div>")
                .toString();
    }

    public static String openCell(String className, String style, String width) throws FrameworkException {
        // class
        String classHtml = BASE_CELL_CLASS + "2";
        if (!BaseFunction.isBlank(width) && width.indexOf("cols") >= 0) {
            BigDecimal bigDecimal = BigDecimalUtil.parseBigDecimal(width.replace("cols", ""));
            classHtml = BASE_CELL_CLASS + bigDecimal.intValue();
        } else if (!BaseFunction.isBlank(className)) {
            classHtml = className;
        }


        return new StringBuilder()
                .append("<div")
                .append(getAttribute("class", classHtml))
                .append(getAttribute("style", !BaseFunction.isBlank(style), style))
                .append(">")
                .toString();

    }

    public static String closeCell() {
        return new StringBuilder()
                .append("</div>")
                .toString();
    }

}
