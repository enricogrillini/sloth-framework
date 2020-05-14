package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.jaxb.form.ForceCase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Input<T> extends InputField<T> {

  private ForceCase forceCase;
  private int maxLength;

  public Input(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null);
  }

  public Input(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
    this(name, alias, description, tooltip, dataType, format, null, false, false, false, ViewModality.VIEW_AUTO, 0, ForceCase.NONE);
  }

  public Input(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality, Integer maxLength, ForceCase forceCase) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
    this.maxLength = maxLength == null ? 0 : maxLength;
    this.forceCase = forceCase;
  }

  @Override
  public void setData(String data) {
    if (getForceCase().equals(ForceCase.INIT_CAP)) {
      data = StringUtil.initCap(data);
    } else if (getForceCase().equals(ForceCase.UPPER)) {
      data = StringUtil.upper(data);
    } else if (getForceCase().equals(ForceCase.LOWER)) {
      data = StringUtil.lower(data);
    }

    super.setData(data);
  }

  @Override
  public FieldType getFieldType() {
    return FieldType.INPUT;
  }

}
