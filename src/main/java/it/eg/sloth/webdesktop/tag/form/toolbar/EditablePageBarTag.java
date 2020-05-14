package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.framework.pageinfo.ViewModality;


/**
 * 
 * Scrive una tool bar per l'editing in una form base
 * 
 * @author Enrico Grillini
 * 
 */
public class EditablePageBarTag extends AbstractToolBarTag {

  private static final long serialVersionUID = 1L;

  @Override
  public int startTag() throws Throwable {
    openLeft();

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    closeLeft();
    openRight();
    
    if (getForm().getPageInfo().getViewModality().equals(ViewModality.VIEW_VISUALIZZAZIONE)) {
      updateButton();
    } else {
      commitButton();
      rollbackButton();
    }
    closeRight();
  }

}
