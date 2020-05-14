package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.casting.DataTypes;

public abstract class TextField<T extends Object> extends AbstractDataField<T> implements DataField<T> {

  private String baseLink;

  public TextField(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null, null);
  }

  public TextField(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tooltip, dataType, format);
    this.baseLink = baseLink;
  }

  public String getBaseLink() {
    return baseLink;
  }

  public void setBaseLink(String baseLink) {
    this.baseLink = baseLink;
  }

}
