package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.grid.Grid;

/**
 * 
 * Scrive una tool bar per la navigazione master detail
 * 
 * @author Enrico Grillini
 * 
 */
public class GridBarTag extends AbstractGridToolBarTag<Grid<?>> {

  private static final long serialVersionUID = 1L;

  @Override
  public int startTag() throws Throwable {
    DataTable<?> dataTable = getElement().getDataSource();
    if (dataTable == null) {
      return SKIP_BODY;
    }

    openLeft();

    // Pulsanti
    firstRowButton(true);
    prevPageButton(true);
    prevButton(true);
    nextButton(true);
    nextPageButton(true);
    lastRowButton(true);
    excelButton();

    // Informazioni di sintesi
    if (dataTable.size() > 0) {
      write(" Rec. " + (dataTable.getCurrentRow() + 1) + " di " + dataTable.size());
      if (dataTable.getPageSize() > 0) {
        write(", Pag. " + (dataTable.getCurrentPage() + 1) + " di " + dataTable.pages());
      }
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
