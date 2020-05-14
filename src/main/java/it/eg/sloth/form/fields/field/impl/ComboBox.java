package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComboBox<T> extends InputField<T> implements DecodedDataField<T> {

  static final long serialVersionUID = 1L;
  
  DecodeMap<T, ? extends DecodeValue<T>> decodeMap;

  public ComboBox(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null);
  }

  public ComboBox(String name, String alias, String description, String tooltip, DataTypes dataType, String format) {
    this(name, alias, description, tooltip, dataType, format, null, false, false, false, ViewModality.VIEW_AUTO);
  }

  public ComboBox(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink, Boolean required, Boolean readOnly, Boolean hidden, ViewModality viewModality) {
    super(name, alias, description, tooltip, dataType, format, baseLink, required, readOnly, hidden, viewModality);
  }
  
  @Override
  public FieldType getFieldType() {
    return FieldType.COMBO_BOX;
  }

  @Override
  public String getDecodedText() {
    if (BaseFunction.isBlank(getData())) {
      return StringUtil.EMPTY;
    } else if (getDecodeMap() == null || getDecodeMap().isEmpty()) {
      return getData();
    } else {
      return getDecodeMap().decode(getValue());
    }
  }

  @Override
  public String getHtmlDecodedText() {
    return getHtmlDecodedText(true, true);
  }

  @Override
  public String getHtmlDecodedText(boolean br, boolean nbsp) {
    return Casting.getHtml(getDecodedText(), br, nbsp);
  }

  @Override
  public String getJsDecodedText() {
    return Casting.getJs(getDecodedText());
  }

}
