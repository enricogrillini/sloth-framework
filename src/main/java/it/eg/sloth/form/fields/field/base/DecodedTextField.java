package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.form.fields.field.DecodedDataField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;

public abstract class DecodedTextField<T extends Object> extends TextField<T> implements DecodedDataField<T> {

  private DecodeMap<T, ? extends DecodeValue<T>> values;

  public DecodedTextField(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null, null);
  }

  public DecodedTextField(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tooltip, dataType, format, baseLink);
    values = null;
    new BaseDecodeMap<T>();
  }

  @Override
  public DecodeMap<T, ? extends DecodeValue<T>> getDecodeMap() {
    return values;
  }

  @Override
  public void setDecodeMap(DecodeMap<T, ? extends DecodeValue<T>> values) {
    this.values = values;
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
