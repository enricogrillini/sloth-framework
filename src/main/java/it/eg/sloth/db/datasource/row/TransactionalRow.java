package it.eg.sloth.db.datasource.row;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.TransactionalDataRow;
import it.eg.sloth.db.query.SelectQueryInterface;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class TransactionalRow extends Row implements TransactionalDataRow {

  protected Map<String, Object> oldValues;
  private RowStatus status;

  public TransactionalRow() {
    super();
    oldValues = new HashMap<>();
    status = RowStatus.INSERTED;
  }

  @Override
  public Object getOldObject(String name) {
    return oldValues.get(name.toLowerCase());
  }

  @Override
  public BigDecimal getOldBigDecimal(String name) {
    return (BigDecimal) oldValues.get(name.toLowerCase());
  }

  @Override
  public Timestamp getOldTimestamp(String name) {
    return (Timestamp) oldValues.get(name.toLowerCase());
  }

  @Override
  public String getOldString(String name) {
    return (String) oldValues.get(name.toLowerCase());
  }

  @Override
  public byte[] getOldByte(String name) {
    return (byte[]) oldValues.get(name.toLowerCase());
  }

  @Override
  public void setObject(String name, Object value) {
    super.setObject(name, value);
    if (status == RowStatus.CLEAN)
      status = RowStatus.UPDATED;
  }

  @Override
  public void loadFromDataSource(DataSource dataSource) {
    super.loadFromDataSource(dataSource);
    oldValues = new HashMap<>(values);
    status = RowStatus.CLEAN;
  }

  @Override
  public void loadFromResultSet(ResultSet resultSet) throws SQLException, IOException {
    super.loadFromResultSet(resultSet);
    oldValues = new HashMap<>(values);
    status = RowStatus.CLEAN;
  }

  @Override
  public void setFromQuery(SelectQueryInterface query) {
    super.setFromQuery(query);
    if (status == RowStatus.CLEAN)
      status = RowStatus.UPDATED;
  }

  @Override
  public boolean loadFromQuery(SelectQueryInterface query) {
    boolean result = super.loadFromQuery(query);
    oldValues = new HashMap<>(values);
    status = RowStatus.CLEAN;

    return result;
  }

  @Override
  public boolean loadFromQuery(SelectQueryInterface query, Connection connection) {
    boolean result = super.loadFromQuery(query, connection);
    oldValues = new HashMap<>(values);
    status = RowStatus.CLEAN;

    return result;
  }

  @Override
  public RowStatus getStatus() {
    return status;
  }

  protected void setStatus(RowStatus status) {
    this.status = status;
  }

  @Override
  public void remove() {
    switch (getStatus()) {
    case INSERTED:
      setStatus(RowStatus.INCONSISTENT);
      break;

    case UPDATED:
      setStatus(RowStatus.DELETED);
      break;

    case CLEAN:
      setStatus(RowStatus.DELETED);
      break;

    default:
      throw new RuntimeException("remove: " + getStatus());
    }
  }

  @Override
  public void save() {
    switch (getStatus()) {
    case INSERTED:
      setStatus(RowStatus.CLEAN);
      break;

    case DELETED:
      setStatus(RowStatus.INCONSISTENT);
      break;

    case UPDATED:
      setStatus(RowStatus.CLEAN);
      break;

    case CLEAN:
      setStatus(RowStatus.CLEAN);
      break;

    default:
      throw new RuntimeException("save: " + getStatus());
    }

    oldValues = new HashMap<>(values);
  }

  @Override
  public void undo() {
    switch (getStatus()) {
    case CLEAN:
      break;

    case INSERTED:
      setStatus(RowStatus.INCONSISTENT);
      break;

    case DELETED:
      setStatus(RowStatus.CLEAN);
      break;

    case UPDATED:
      setStatus(RowStatus.CLEAN);
      break;

    default:
      throw new RuntimeException("undo: " + getStatus());
    }

    values = new HashMap<String, Object>(oldValues);
  }

  @Override
  public void forceClean() {
    setStatus(RowStatus.CLEAN);
    oldValues = new HashMap<>(values);
  }

}
