package it.eg.sloth.db.datasource.row;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.framework.FrameComponent;
import lombok.extern.slf4j.Slf4j;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Slf4j
public class Row extends FrameComponent implements DataRow {

  protected Map<String, Object> values;

  public Row() {
    values = new LinkedHashMap<String, Object>();
  }

  public Row(DataSource dataSource) {
    this();
    copyFromDataSource(dataSource);
  }

  @Override
  public Object getObject(String name) {
    return values.get(name.toLowerCase());
  }

  @Override
  public BigDecimal getBigDecimal(String name) {
    return (BigDecimal) values.get(name.toLowerCase());
  }

  @Override
  public Timestamp getTimestamp(String name) {
    return (Timestamp) values.get(name.toLowerCase());
  }

  @Override
  public String getString(String name) {
    return (String) values.get(name.toLowerCase());
  }

  @Override
  public byte[] getByte(String name) {
    return (byte[]) values.get(name.toLowerCase());
  }

  @Override
  public void setObject(String name, Object value) {
    if (getObject(name) != null)
      values.remove(name.toLowerCase());
    values.put(name.toLowerCase(), value);
  }

  @Override
  public void setBigDecimal(String name, BigDecimal value) {
    setObject(name, value);
  }

  @Override
  public void setTimestamp(String name, Timestamp value) {
    setObject(name, value);
  }

  @Override
  public void setString(String name, String value) {
    setObject(name, value);
  }

  @Override
  public void setByte(String name, byte[] value) {
    setObject(name, value);
  }

  @Override
  public void clear() {
    values.clear();
  }

  @Override
  public Iterator<String> keyIterator() {
    return values.keySet().iterator();
  }

  @Override
  public Iterator<Object> valueIterator() {
    return values.values().iterator();
  }

  @Override
  public void copyFromDataSource(DataSource dataSource) {
    Iterator<String> iterator = dataSource.keyIterator();
    while (iterator.hasNext()) {
      String key = iterator.next();
      setObject(key, dataSource.getObject(key));
    }
  }

  protected void copyFromResultSet(ResultSet resultSet, int i) throws SQLException, IOException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    switch (resultSetMetaData.getColumnType(i)) {
    case Types.VARCHAR:
    case Types.CHAR:
      setString(resultSetMetaData.getColumnName(i), resultSet.getString(i));
      break;

    case Types.BIT:
    case Types.SMALLINT:
    case Types.TINYINT:
    case Types.INTEGER:
    case Types.BIGINT:
    case Types.FLOAT:
    case Types.DECIMAL:
    case Types.REAL:
    case Types.DOUBLE:
    case Types.NUMERIC:
      setBigDecimal(resultSetMetaData.getColumnName(i), resultSet.getBigDecimal(i));
      break;

    case Types.DATE:
    case Types.TIME:
    case Types.TIMESTAMP:
      setTimestamp(resultSetMetaData.getColumnName(i), resultSet.getTimestamp(i));
      break;

    default:
      log.debug("populateDataRow - Types {} non gestito. Utilizzato default.", resultSetMetaData.getColumnType(i));
      setObject(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
      break;
    }
  }

  public void copyFromResultSet(ResultSet resultSet) throws SQLException, IOException {
    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
      copyFromResultSet(resultSet, i);
    }
  }

  @Override
  public void loadFromResultSet(ResultSet resultSet) throws SQLException, IOException {
    clear();
    copyFromResultSet(resultSet);
  }

  @Override
  public void loadFromDataSource(DataSource dataSource) {
    clear();
    copyFromDataSource(dataSource);
  }

  @Override
  public void setFromQuery(SelectQueryInterface query) {
    try {
      query.populateDataRow(this);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void setFromQuery(SelectQueryInterface query, Connection connection) {
    try {
      query.populateDataRow(this, connection);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean loadFromQuery(SelectQueryInterface query) {
    try {
      return query.populateDataRow(this);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean loadFromQuery(SelectQueryInterface query, Connection connection) {
    try {
      return query.populateDataRow(this, connection);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  protected String valuesAsString() {
    return values.toString();
  }

}
