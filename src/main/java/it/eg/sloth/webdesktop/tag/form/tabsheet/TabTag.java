package it.eg.sloth.webdesktop.tag.form.tabsheet;

import it.eg.sloth.form.tabSheet.Tab;
import it.eg.sloth.form.tabSheet.TabSheet;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

public class TabTag extends BaseElementTag<Tab> {

  private static final long serialVersionUID = 1L;

  protected TabSheet getTabSheet() {
    return (TabSheet) getParentElement();
  }

  public int startTag() throws Throwable {
    TabSheet tabSheet = getTabSheet();
    if (tabSheet.getCurrentTab() == getElement()) {
      return EVAL_BODY_INCLUDE;
    } else {
      return SKIP_BODY;
    }
  }

  protected void endTag() throws Throwable {
  }

}
