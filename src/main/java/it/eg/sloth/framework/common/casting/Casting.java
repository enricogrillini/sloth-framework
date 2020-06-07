package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import org.apache.commons.text.StringEscapeUtils;

/**
 * @author Enrico Grillini
 * <p>s
 * Questa classe implemente i controlli formali sui tipi dato
 */
public class Casting {

    private Casting() {
        // NOP
    }

    /**
     * Converte l'oggetto passato in una stringa HTML
     *
     * @param testo
     * @return
     */
    public static String getHtml(String testo) {
        return getHtml(testo, true, true);
    }

    /**
     * Converte l'oggetto passato in una stringa HTML
     *
     * @param testo
     * @return
     */
    public static String getHtml(String testo, boolean br, boolean nbsp) {
        if (BaseFunction.isNull(testo)) {
            return StringUtil.EMPTY;
        }

        String result = StringEscapeUtils.escapeHtml4(testo);

        if (br) {
            result = result.replaceAll("\\n", "<br/>");
            result = result.replaceAll("\\r", "");
        }

        if (nbsp) {
            result = result.replace("  ", "&nbsp; ");
        }

        return result;
    }

    /**
     * Converte l'oggetto passato in una stringa Js
     *
     * @param testo
     * @return
     */
    public static String getJs(String testo) {
        if (BaseFunction.isBlank(testo)) {
            return StringUtil.EMPTY;
        } else {
            return StringEscapeUtils.escapeJson(testo);
        }
    }

}
