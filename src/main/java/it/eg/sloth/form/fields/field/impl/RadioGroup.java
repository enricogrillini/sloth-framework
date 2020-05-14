package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;

public class RadioGroup<T> extends ComboBox<T> {

  

  public RadioGroup(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null);
  }

  public RadioGroup(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
    this(name, alias, description, tooltip, dataType, format, null, false, false, false, ViewModality.VIEW_AUTO);
  }

  public RadioGroup(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
  }
  
  @Override
  public FieldType getFieldType() {
    return FieldType.RADIO_GROUP;
  }

}
