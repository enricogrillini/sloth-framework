package it.eg.sloth.form.chart.element;

import java.math.BigDecimal;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;

public class Series extends AbstractChartField<BigDecimal> {

  public Series(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tootip, dataType, format, baseLink);
  }
  
  @Override
  public FieldType getFieldType() {
    return FieldType.SERIES;
  }
}
