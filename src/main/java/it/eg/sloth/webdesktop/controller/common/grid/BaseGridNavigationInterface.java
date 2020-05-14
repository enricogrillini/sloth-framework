package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.controller.common.SimpleSearchPageInterface;

/**
 * 
 * Gestisce l'interfaccia di base per la navigazione sulle griglie
 * 
 * @author Enrico Grillini
 * 
 */
public interface BaseGridNavigationInterface extends SimpleSearchPageInterface {

  public void onGoToRecord(int record) throws Exception;

  public void onFirstRow() throws Exception;

  public void onPrevPage() throws Exception;

  public void onPrev() throws Exception;

  public void onNext() throws Exception;

  public void onNextPage() throws Exception;

  public void onLastRow() throws Exception;

  public void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception;

  public void onExcel(Grid<?> grid) throws Exception;
}
