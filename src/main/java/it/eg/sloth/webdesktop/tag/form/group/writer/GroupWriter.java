package it.eg.sloth.webdesktop.tag.form.group.writer;

import java.math.BigDecimal;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;


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

    public static String openCell(String className, String style, String width) throws BusinessException {
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
