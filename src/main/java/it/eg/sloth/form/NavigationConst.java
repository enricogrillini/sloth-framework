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

  // Inizializzazione pagina
  public static final String INIT = "init";

  // Elaborazioni
  public static final String START = "start";
  public static final String STATUS = "status";
  public static final String RESULT = "result";

  // Base
  public static final String LOAD = "load";
  public static final String RESET = "reset";

  // POPUP
  public static final String PARENT_OPEN_POPUP = "parentOpenPopup";
  public static final String PARENT_SELECT_POPUP = "parentSelectPopup";
  public static final String PARENT_CLOSE_POPUP = "parentClosePopup";

  public static final String CHILD_OPEN_POPUP = "childOpenPopup";
  public static final String CHILD_SELECT_POPUP = "childSelectPopup";
  public static final String CHILD_CLOSE_POPUP = "childClosePopup";

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

  
  public static String navStr(String str1) {
    return WebRequest.PREFIX + WebRequest.SEPARATOR + str1;
  }

  public static String navStr(String str1, String str2) {
    return navStr(str1) + WebRequest.SEPARATOR + str2;
  }

  public static String navStr(String str1, String str2, String str3) {
    return navStr(str1, str2) + WebRequest.SEPARATOR + str3;
  }

  public static String navStr(String str1, String str2, String str3, String str4) {
    return navStr(str1, str2, str3) + WebRequest.SEPARATOR + str4;
  }

  public static String concat(String str1, String str2) {
    return str1 + WebRequest.SEPARATOR + str2;
  }

  public static String concat(String str1, String str2, String str3) {
    return concat(str1, str2) + WebRequest.SEPARATOR + str3;
  }

  public static String concat(String str1, String str2, String str3, String str4) {
    return concat(str1, str2, str3) + WebRequest.SEPARATOR + str4;
  }

}
