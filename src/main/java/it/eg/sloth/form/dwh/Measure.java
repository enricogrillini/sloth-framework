package it.eg.sloth.form.dwh;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.casting.DataTypes;

public class Measure<T> extends TextField<T> {

  

  public Measure(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tootip, dataType, format, baseLink);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.MEASURE;
  }
  
}
