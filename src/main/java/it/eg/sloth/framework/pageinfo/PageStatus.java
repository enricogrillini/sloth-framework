package it.eg.sloth.framework.pageinfo;

/**
 * 
 * Gestione dello sto di una pagina
 * 
 * @author Enrico Grillini
 * 
 */
public enum PageStatus {
  MASTER, DETAIL, UPDATING, INSERTING, DELETING;

  public boolean isClean() {
    return this == MASTER || this == DETAIL;
  }

  public boolean isChanging() {
    return !isClean();
  }
}
