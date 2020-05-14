package it.eg.sloth.form.tabSheet;

import it.eg.sloth.form.base.AbstractElements;

public class TabSheet extends AbstractElements<Tab> {

  

  private String currentTabName;

  public TabSheet(String name) {
    super(name);
  }

  public String getCurrentTabName() {
    return currentTabName;
  }

  public void setCurrentTabName(String currentTabName) {
    this.currentTabName = currentTabName;
  }

  public Tab getCurrentTab() {
    if (getElements() == null || getElements().size() == 0)
      return null;

    if (getCurrentTabName() == null)
      return (Tab) getElements().get(0);

    if (getElement(getCurrentTabName()) != null)
      return (Tab) getElement(getCurrentTabName());

    return (Tab) getElements().get(0);
  }

}
