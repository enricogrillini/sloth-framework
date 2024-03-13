package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Questa classe implemente i controlli formali sui tipi dato
 *
 * @author Enrico Grillini
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
            result = result.replace("\n", "<br>");
            result = result.replace("\r", "");
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
