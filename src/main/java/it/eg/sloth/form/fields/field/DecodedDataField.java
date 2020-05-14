package it.eg.sloth.form.fields.field;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;

public interface DecodedDataField<T extends Object> extends DataField<T> {

  public DecodeMap<T,  ? extends DecodeValue<T>> getDecodeMap();

  public void setDecodeMap(DecodeMap<T, ? extends DecodeValue<T>> values);

  public String getDecodedText();

  public String getHtmlDecodedText();

  public String getHtmlDecodedText(boolean br, boolean nbsp);

  public String getJsDecodedText();
}
