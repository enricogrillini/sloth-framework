package it.eg.sloth.form;


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
 *
 * @author Enrico Grillini
 */
public class NavigationConst {

    private NavigationConst() {
        // NOP
    }

    // Inizializzazione pagina
    public static final String INIT = "init";

    // Elaborazioni
    public static final String START = "start";
    public static final String STATUS = "status";
    public static final String RESULT = "result";

    // Base
    public static final String LOAD = "load";
    public static final String RESET = "reset";

    // Navigazione elenchi
    public static final String BACK = "back";
    public static final String FIRST_ROW = "firstpage";
    public static final String PREV_PAGE = "prevpage";
    public static final String PREV = "prev";
    public static final String ELENCO = "elenco";
    public static final String ROW = "row";
    public static final String NEXT = "next";
    public static final String NEXT_PAGE = "nextpage";
    public static final String LAST_ROW = "lastrow";
    public static final String EXCEL = "excel";
    public static final String SORT_ASC = "sortasc";
    public static final String SORT_DESC = "sortdesc";

    // Salvataggio
    public static final String DELETE = "delete";
    public static final String INSERT = "insert";
    public static final String UPDATE = "update";
    public static final String COMMIT = "commit";
    public static final String ROLLBACK = "rollback";

    // TabPage
    public static final String TAB = "tab";

    // Button
    public static final String BUTTON = "button";

    // Button
    public static final String AUTOCOMPLETE = "autocomplete";


    public static String navStr(String... str) {
        StringBuilder result = new StringBuilder(WebRequest.PREFIX);
        for (String string : str) {
            result.append(WebRequest.SEPARATOR + string);
        }
        return result.toString();
    }


    public static String hrefStr(String baseUrl, String... str) {
        return baseUrl + "?" + navStr(str) + "=x";
    }

}
