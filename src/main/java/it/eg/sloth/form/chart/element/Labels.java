package it.eg.sloth.form.chart.element;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Labels<T> extends AbstractChartField<T> {

  int rotation;

  public Labels(String name, String alias, String description, String tootip, DataTypes dataType, String format, String baseLink, Integer rotation) {
    super(name, alias, description, tootip, dataType, format, baseLink);
    this.rotation = rotation == null ? 0 : rotation;
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.LABELS;
  }

}
