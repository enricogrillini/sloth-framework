package it.eg.sloth.db.decodemap.map;

import java.io.IOException;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.value.BaseDecodeValue;
import it.eg.sloth.db.query.SelectQueryInterface;

public class BaseDecodeMap<T> extends AbstractDecodeMap<T, BaseDecodeValue<T>> {

  

  public BaseDecodeMap() {
    super();
  }

  public BaseDecodeMap(DataTable<?> dataTable, String codeName, String descriptionName) {
    this(dataTable, codeName, descriptionName, null);
  }

  @SuppressWarnings("unchecked")
  public BaseDecodeMap(DataTable<?> dataTable, String codeName, String descriptionName, String validName) {
    super();

    for (DataRow row : dataTable) {
      if (validName == null)
        put((T) row.getObject(codeName), row.getString(descriptionName));
      else
        put((T) row.getObject(codeName), row.getString(descriptionName), "S".equals(row.getString(validName)));
    }
  }

  public BaseDecodeMap(SelectQueryInterface query) throws SQLException, IOException {
    this(query, DecodeValue.DEFAULT_CODE_NAME, DecodeValue.DEFAULT_DESCRIPTION_NAME, null);
  }

  public BaseDecodeMap(SelectQueryInterface query, String codeName, String descriptionName, String validName) throws SQLException, IOException {
    this(query.selectTable(), codeName, descriptionName, validName);
  }

  public void put(T code, String description) {
    put(new BaseDecodeValue<T>(code, description));
  }

  public void put(T code, String description, boolean valid) {
    put(new BaseDecodeValue<T>(code, description, valid));
  }


}
