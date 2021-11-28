package it.eg.sloth.webdesktop.tag.form;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;

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
 * <p>
 * Classe base per la stampa il testo di un controllo
 *
 * @author Enrico Grillini
 */
public abstract class HtmlWriter {

    private static final String LINK = "<a href=\"{0}\" >{1}</a>";
    private static final String LINK_EXTERNAL = "<a href=\"{0}\" target=\"_blank\">{1}</a>";

    public static final String NBSP = "&nbsp;";

    public static final String BEGIN_BUTTON = "<button";
    public static final String BEGIN_INPUT = "<input";

    public static final String CLOSE_DIV = "</div>";

    public static final String ON_CLICK = "onclick";

    public static final String ATTR_CLASS = "class";
    public static final String ATTR_CHECKED = "checked";
    public static final String ATTR_DATA_DESCRIPTION = "data-description";
    public static final String ATTR_DATA_TITLE = "data-title";
    public static final String ATTR_DISABLED = "disabled";
    public static final String ATTR_FOR = "for";
    public static final String ATTR_HREF = "href";
    public static final String ATTR_ID = "id";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_PLACEHOLDER = "placeholder";
    public static final String ATTR_TARGET = "target";
    public static final String ATTR_TYPE = "type";
    public static final String ATTR_READONLY = "readonly";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_VALUE = "value";

    private static final String TOOLTIP = " data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"{0}\"";
    private static final String ATTR_POPOVER = "data-toggle=\"popover\" data-placement=\"bottom\" data-container=\"body\" data-trigger=\"hover\" data-html=\"true\" data-original-title=\"{0}\" data-content=\"{1}\"";

    public static final String VAL_ATTR_TYPE_RADIO = "radio";
    public static final String VAL_ATTR_TYPE_CHECKBOX = "checkbox";

    public static String getElement(String element) {
        return "<" + element + ">";
    }

    public static String getElement(String element, String value) {
        return "<" + element + ">" + Casting.getHtml(value) + "</" + element + ">";
    }

    public static String getElement(String element, boolean condizione, String value) {
        if (condizione) {
            return getElement(element, value);
        } else {
            return StringUtil.EMPTY;
        }
    }

    public static String getAttribute(String property, String value) {
        return " " + property + "=\"" + value + "\"";
    }

    public static String getAttribute(String property, boolean condizione, String value) {
        return condizione ? " " + property + "=\"" + value + "\"" : StringUtil.EMPTY;
    }

    public static String getAttribute(String property, boolean condizione, String valTrue, String valFalse) {
        return condizione ? " " + property + "=\"" + valTrue + "\"" : " " + property + "=\"" + valFalse + "\"";
    }

    public static String getTooltipAttributes(String tooltip) {
        if (BaseFunction.isBlank(tooltip)) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(TOOLTIP, Casting.getHtml(tooltip));
        }
    }

    public static String getAttributePopover(String title, String htmlBody) {
        return MessageFormat.format(ATTR_POPOVER, title, htmlBody);
    }

    public static String getLink(String url, String html) {
        return getLink(url, html, false);
    }

    public static String getLink(String url, String html, boolean external) {
        if (external) {
            return MessageFormat.format(LINK_EXTERNAL, url, html);
        } else {
            return MessageFormat.format(LINK, url, html);
        }
    }

}
