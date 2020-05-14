package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.form.tabSheet.Tab;
import it.eg.sloth.form.tabSheet.TabSheet;

/**
 * 
 * Gestisce l'interfaccia per la navigazione su un master detail
 * 
 * @author Enrico Grillini
 * 
 */
public interface MasterDetailGridNavigationInterface extends BaseGridNavigationInterface {

  public void execLoadDetail() throws Exception;
  
  public void execLoadSubDetail(Grid<?> grid) throws Exception;

  public void onElenco() throws Exception;

  public void execSelectTab(TabSheet tabSheet, Tab tab) throws Exception;

  public void onSelectTab(String tabSheetName, String tabName) throws Exception;

  public void onSubGoToRecord(Grid<?> grid, int record) throws Exception;

  public void onSubFirstRow(Grid<?> grid) throws Exception;

  public void onSubPrevPage(Grid<?> grid) throws Exception;

  public void onSubPrev(Grid<?> grid) throws Exception;

  public void onSubNext(Grid<?> grid) throws Exception;

  public void onSubNextPage(Grid<?> grid) throws Exception;

  public void onSubLastRow(Grid<?> grid) throws Exception;
}
