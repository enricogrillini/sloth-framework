package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.datasource.DataRow;

public class TableDecodeValue<T, R extends DataRow> extends AbstractDecodeValue<T> {

  private R dataRow;
  private String codeName;
  private String descriptionName;

  public TableDecodeValue(R dataRow, String codeName, String descriptionName) {
    this(dataRow, codeName, descriptionName, true);
  }

  public TableDecodeValue(R dataRow, String codeName, String descriptionName, boolean valid) {
    this.dataRow = dataRow;
    this.codeName = codeName;
    this.descriptionName = descriptionName;
    setValid(valid);
  }

  @SuppressWarnings("unchecked")
  @Override
  public T getCode() {
    return (T) dataRow.getObject(codeName);
  }

  @Override
  public String getDescription() {
    return dataRow.getString(descriptionName);
  }
  
  public R getDataRow() {
    return dataRow;
  }


}
