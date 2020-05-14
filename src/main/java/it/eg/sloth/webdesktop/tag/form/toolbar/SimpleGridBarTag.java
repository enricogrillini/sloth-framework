package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.grid.Grid;

/**
 * 
 * Scrive una tool bar per un elenco base
 * 
 * @author Enrico Grillini
 * 
 */
public class SimpleGridBarTag extends AbstractGridToolBarTag<Grid<?>> {

  private static final long serialVersionUID = 1L;

  private boolean navigation = true;
  private boolean excel = true;

  public boolean isNavigation() {
    return navigation;
  }

  public void setNavigation(boolean navigation) {
    this.navigation = navigation;
  }

  public boolean isExcel() {
    return excel;
  }

  public void setExcel(boolean excel) {
    this.excel = excel;
  }

  @Override
  public int startTag() throws Throwable {
    DataTable<?> dataTable = getElement().getDataSource();
    if (dataTable == null) {
      return SKIP_BODY;
    }

    openLeft();

    // Pulsanti di navigazione
    if (isNavigation()) {
      firstRowButton(false);
      prevPageButton(false);
      nextPageButton(false);
      lastRowButton(false);

      // Informazioni di sintesi
      if (dataTable.size() > 0) {
        write(" Rec. " + (dataTable.getCurrentRow() + 1) + " di " + dataTable.size());
        if (dataTable.getPageSize() > 0) {
          write(", Pag. " + (dataTable.getCurrentPage() + 1) + " di " + dataTable.pages());
        }
      }
    } else {
      write("&nbsp;Rec. " + dataTable.size());
    }

    // Estrazione Excel
    if (isExcel()) {
      excelButton();
    }

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    if (getElement().getDataSource() == null) {
      return;
    }

    closeLeft();
  }

}
