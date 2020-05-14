package it.eg.sloth.form;


/**
 * @author Enrico Grillini
 * 
 * Costanti utilizzate dal coordinatore
 */

public class NavigationConst {


  // Bradiplet
  public static final String BRADIPLET = "bradiplet";
  
  public static final String LINK = "link";
  public static final String MINIMIZE = "minimize";
  public static final String MAXIMIZE = "maximize";
  public static final String ICONIZE = "iconize";
  public static final String CLOSE = "close";

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
