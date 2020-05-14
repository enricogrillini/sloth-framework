package it.eg.sloth.form.dwh;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.DecodedTextField;
import it.eg.sloth.framework.common.casting.DataTypes;

public class Attribute<T> extends DecodedTextField<T> {

  

  public Attribute(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tootip, dataType, format, baseLink);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.ATTRIBUTE;
  }
  
}
