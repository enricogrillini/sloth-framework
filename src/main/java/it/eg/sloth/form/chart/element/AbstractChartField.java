package it.eg.sloth.form.chart.element;

import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.casting.DataTypes;


public abstract class AbstractChartField<T> extends TextField<T> {

  public AbstractChartField(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tootip, dataType, format, baseLink);
  }
}
