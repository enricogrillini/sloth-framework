package it.eg.sloth.db.datasource;

import java.sql.Connection;

import it.eg.sloth.db.datasource.row.column.Column;

public interface DbDataRow extends DbDataSource, TransactionalDataRow {

  /**
   * Restituisce le specifiche delle colonne della riga
   * 
   * @return
   */
  public Column[] getColumns();

  public String getSelect();

  public String getInsert();

  public String getDelete();

  public String getUpdate();

  public boolean select();

  public boolean select(Connection connection);

  public void insert();

  public void insert(Connection connection);

  public void delete();

  public void delete(Connection connection);

  public void update();

  public void update(Connection connection);

  public boolean isAutoloadLob();

  public void setAutoloadLob(boolean autoloadLob);

}
