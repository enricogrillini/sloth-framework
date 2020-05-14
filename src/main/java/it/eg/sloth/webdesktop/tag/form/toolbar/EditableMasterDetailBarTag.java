package it.eg.sloth.webdesktop.tag.form.toolbar;

/**
 * 
 * Scrive una tool bar per la navigazione master detail
 * 
 * @author Enrico Grillini
 * 
 */
public class EditableMasterDetailBarTag extends MasterDetailBarTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected void endTag() throws Throwable {
    if (getElement().getDataSource() == null) {
      return;
    }
    
    closeLeft();
    
    if (getElement().getDataSource() != null) {
      openRight();

      if (getForm().getPageInfo().getPageStatus().isClean()) {
        insertButton();
        deleteButton();
        updateButton();
      } else {
        commitButton();
        rollbackButton();
      }

      closeRight();
    }
  }

}
