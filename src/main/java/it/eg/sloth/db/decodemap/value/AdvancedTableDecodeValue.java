package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.datasource.DataRow;

public class AdvancedTableDecodeValue<T, R extends DataRow> extends TableDecodeValue<T, R> {

  private String subDescriptionName;
  private String urlImmagine;

  public AdvancedTableDecodeValue(R dataRow, String codeName, String descriptionName, String subDescriptionName, String urlImmagine, boolean valid) {
    super(dataRow, codeName, descriptionName, valid);
  }

  public String getSubDescriptionName() {
    return subDescriptionName;
  }

  public void setSubDescriptionName(String subDescriptionName) {
    this.subDescriptionName = subDescriptionName;
  }

  public String getUrlImmagine() {
    return urlImmagine;
  }

  public void setUrlImmagine(String urlImmagine) {
    this.urlImmagine = urlImmagine;
  }

}
