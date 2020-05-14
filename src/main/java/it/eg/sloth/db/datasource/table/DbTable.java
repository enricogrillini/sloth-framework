package it.eg.sloth.db.datasource.table;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;

import it.eg.sloth.db.datasource.DbDataRow;
import it.eg.sloth.db.datasource.DbDataTable;
import it.eg.sloth.db.manager.DataConnectionManager;

/**
 * 
 * @author Enrico Grillini
 * 
 * @param <T>
 */
public abstract class DbTable<T extends DbDataRow> extends TransactionalTableAbstract<T> implements DbDataTable<T> {

  private boolean autoloadLob;

  public DbTable() {
    this.autoloadLob = false;
  }

  @Override
  public void post(Connection connection) {
    for (T row : rowsDeleted) {
      row.post(connection);
    }

    for (T row : this) {
      row.post(connection);
    }
  }

  @Override
  public void unPost() {
    for (T row : rowsDeleted) {
      row.unPost();
    }

    for (T row : this) {
      row.unPost();
    }
  }

  @Override
  public void commit() {
    for (T row : rowsDeleted) {
      row.commit();
    }

    for (T row : this) {
      row.commit();
    }

    oldRows = new ArrayList<T>(rows);
    rowsDeleted = new HashSet<T>();
  }

  @Override
  public void rollback() {
    rows = new ArrayList<T>(oldRows);
    rowsDeleted = new HashSet<T>();

    for (T row : this) {
      row.rollback();
    }
  }

  @Override
  public void save() {
    Connection connection = null;
    try {
      connection = DataConnectionManager.getInstance().getConnection();
      connection.setAutoCommit(false);
      post(connection);
      commit();
      connection.commit();

    } catch (Throwable e) {
      DataConnectionManager.rollback(connection);
      throw new RuntimeException(e);

    } finally {
      DataConnectionManager.release(connection);
    }
  }

  @Override
  public boolean isAutoloadLob() {
    return autoloadLob;
  }

  @Override
  public void setAutoloadLob(boolean autoloadLob) {
    this.autoloadLob = autoloadLob;
  }

}
