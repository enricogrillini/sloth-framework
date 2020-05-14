package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.DecodedTextField;
import it.eg.sloth.framework.common.casting.DataTypes;

public class DecodedText<T> extends DecodedTextField<T> {

  

  public DecodedText(String name, String description, String tooltip, DataTypes dataType) {
    super(name, description, tooltip, dataType);
  }

  public DecodedText(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tooltip, dataType, format, baseLink);
  }
  
  @Override
  public FieldType getFieldType() {
    return FieldType.DECODED_TEXT;
  }

}
