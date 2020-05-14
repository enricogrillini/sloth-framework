package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.controller.common.SimpleSearchPageInterface;

/**
 * 
 * Gestisce l'interfaccia per la navigazione su una pagina di report
 * 
 * @author Enrico Grillini
 * 
 */
public interface ReportGridNavigationInterface extends SimpleSearchPageInterface {

  public void onFirstRow(Grid<?> grid) throws Exception;

  public void onPrevPage(Grid<?> grid) throws Exception;

  public void onNextPage(Grid<?> grid) throws Exception;

  public void onLastRow(Grid<?> grid) throws Exception;

  public void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception;

  public void onExcel(Grid<?> grid) throws Exception;

}
