package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;

public class TextArea<T> extends Input<T> {

  

  public TextArea(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null);
  }

  public TextArea(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
    this(name, alias, description, tooltip, dataType, format, null, false, false, false, ViewModality.VIEW_AUTO, 0, ForceCase.NONE);
  }

  public TextArea(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, Integer maxLength, ForceCase forceCase) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality, maxLength, forceCase);
  }
  
  @Override
  public FieldType getFieldType() {
    return FieldType.TEXT_AREA;
  }

}
