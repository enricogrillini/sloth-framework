package it.eg.sloth.db.datasource.table;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.query.SelectQueryInterface;

/**
 * @author Enrico Grillini
 * 
 *         Implementa la gestione di un elenco
 * 
 */
public class Table extends TableAbstract<Row> {

  

  public Table(DataTable<?> dataTable) {
    super(dataTable);
  }

  public Table() {
    super();
  }

  @Override
  protected Row createRow() {
    return new Row();
  }

  @Override
  protected Table newTable() {
    return new Table();
  }

  public static class Factory {

    public static Table loadFromQuery(SelectQueryInterface query, int pageSize) {
      Table tableBean = new Table();
      tableBean.loadFromQuery(query);
      tableBean.setPageSize(pageSize);
      return tableBean;
    }

    public static Table loadFromQuery(SelectQueryInterface query) {
      return loadFromQuery(query, -1);
    }
  }

}
