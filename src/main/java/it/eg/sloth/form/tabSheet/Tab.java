package it.eg.sloth.form.tabSheet;

import it.eg.sloth.form.base.AbstractElement;

public class Tab extends AbstractElement {

  
  
  private String description;
  private boolean hidden;
  private boolean disabled;

  public Tab(String name, String description) {
    this(name, description, false, false);
  }

  public Tab(String name, String description, Boolean hidden, Boolean disabled) {
    super(name);
    this.description = description;
    this.hidden = hidden == null ? false : hidden;
    this.disabled = disabled == null ? false : disabled;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
