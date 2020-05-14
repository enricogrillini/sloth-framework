package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.pageinfo.ViewModality;

/**
 * 
 * Scrive una tool bar per l'editing in una elenco
 * 
 * @author Enrico Grillini
 * 
 */
public class EditableGridBarTag extends AbstractGridToolBarTag<Grid<?>> {

  private static final long serialVersionUID = 1L;

  @Override
  public int startTag() throws Throwable {
    DataTable<?> dataTable = getElement().getDataSource();
    if (dataTable == null) {
      return SKIP_BODY;
    }

    openLeft();

    // Pulsanti
    firstRowButton(false);
    prevPageButton(false);
    prevButton(false);
    nextButton(false);
    nextPageButton(false);
    lastRowButton(false);

    // Informazioni di sintesi
    if (dataTable.size() > 0) {
      write(" Rec. " + (dataTable.getCurrentRow() + 1) + " di " + dataTable.size());
      if (dataTable.getPageSize() > 0) {
        write(", Pag. " + (dataTable.getCurrentPage() + 1) + " di " + dataTable.pages());
      }
    }

    excelButton();

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    if (getElement().getDataSource() == null) {
      return;
    }

    closeLeft();
    openRight();

    insertButton();
    deleteButton();
    if (getForm().getPageInfo().getViewModality().equals(ViewModality.VIEW_VISUALIZZAZIONE)) {
      updateButton();
    } else {
      commitButton();
      rollbackButton();
    }
    closeRight();
  }

}
