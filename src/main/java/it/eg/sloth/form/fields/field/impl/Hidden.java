package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;

public class Hidden<T> extends InputField<T> {

   static final long serialVersionUID = 1L;

  public Hidden(String name, String description, String tooltip, DataTypes dataType) {
    super(name, description, tooltip, dataType);
  }

  public Hidden(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, false, false, ViewModality.VIEW_AUTO);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.HIDDEN;
  }
  
}
