package it.eg.sloth.webdesktop.tag.form;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;

/**
 * Classe base per la stampa il testo di un controllo
 *
 * @author Enrico Grillini
 */
public abstract class AbstractHtmlWriter {


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
