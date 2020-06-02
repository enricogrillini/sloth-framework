package it.eg.sloth.webdesktop.tag.form;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;

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
 * <p>
 * Classe base per la stampa il testo di un controllo
 *
 * @author Enrico Grillini
 */
public abstract class AbstractHtmlWriter {

    public static final String ATTR_ID = "id";
    public static final String ATTR_NAME = "name";
    public static final String ATTR_VALUE = "value";
    public static final String ATTR_FOR = "for";

    public static final String ATTR_CLASS = "class";
    public static final String ATTR_STYLE = "style";
    public static final String ATTR_READONLY = "readonly";
    public static final String ATTR_DISABLED ="disabled";
    public static final String ATTR_CHECKED ="checked";



    protected static String getElement(String element) {
        return "<" + element + ">";
    }

    protected static String getElement(String element, String value) {
        return "<" + element + ">" + Casting.getHtml(value) + "</" + element + ">";
    }

    protected static String getElement(String element, boolean condizione, String value) {
        if (condizione) {
            return getElement(element, value);
        } else {
            return StringUtil.EMPTY;
        }
    }

    protected static String getAttribute(String property, String value) {
        return " " + property + "=\"" + value + "\"";
    }

    protected static String getAttribute(String property, boolean condizione, String value) {
        return condizione ? " " + property + "=\"" + value + "\"" : "";
    }

    protected static String getAttribute(String property, boolean condizione, String valTrue, String valFalse) {
        return condizione ? " " + property + "=\"" + valTrue + "\"" : " " + property + "=\"" + valFalse + "\"";
    }

}
