package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.decodemap.value.TableDecodeValue;

public class TableDecodeMap<T, R extends DataRow> extends AbstractDecodeMap<T, TableDecodeValue<T, R>> {

 
  public TableDecodeMap() {
    super();
  }

  public TableDecodeMap(DataTable<R> dataTable, String codeName, String descriptionName, String validName) {
    super();
    load(dataTable, codeName, descriptionName, validName);
  }

  public void load(DataTable<R> dataTable, String codeName, String descriptionName, String validName) {
    clear();

    if (dataTable != null) {
      for (R dataRow : dataTable) {
        TableDecodeValue<T, R> tableDecodeValue;
        if (validName == null) {
          tableDecodeValue = new TableDecodeValue<T, R>(dataRow, codeName, descriptionName);
        } else {
          tableDecodeValue = new TableDecodeValue<T, R>(dataRow, codeName, descriptionName, "S".equals(dataRow.getString(validName)));
        }

        put(tableDecodeValue);
      }
    }
  }

  public void load(DataTable<R> dataTable, String codeName, String descriptionName) {
    load(dataTable, codeName, descriptionName, null);
  }

  public R getRowBean(T code) {
    if (contains(code)) {
      return get(code).getDataRow();
    } else {
      return null;
    }
  }

}
