package it.eg.sloth.db.datasource;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;

/**
 * @author Enrico Grillini
 * 
 *         Implementa la gestione di una riga di un elenco
 * 
 */
public interface DataSource {

  public Object getObject(String name);

  public BigDecimal getBigDecimal(String name);

  public Timestamp getTimestamp(String name);

  public String getString(String name);

  public byte[] getByte(String name);

  public void setObject(String name, Object value);

  public void setBigDecimal(String name, BigDecimal value);

  public void setTimestamp(String name, Timestamp value);

  public void setString(String name, String value);

  public void setByte(String name, byte[] value);

  public void clear();

  public Iterator<String> keyIterator();

  public Iterator<Object> valueIterator();

  /**
   * Aggiorna il DataSource prelevando le informazioni dal DataSource passato
   * 
   * @param dataSource
   */
  public void copyFromDataSource(DataSource dataSource);

  /**
   * Aggiorna il DataSource prelevando le informazioni dal ResultSet passato
   * 
   * @param resultSet
   * @throws SQLException
   */
  public void copyFromResultSet(ResultSet resultSet) throws SQLException, IOException;

  /**
   * Carica il DataSource prelevando le informazioni dal DataSource passato
   * 
   * @param dataSource
   */
  public void loadFromDataSource(DataSource dataSource);

  /**
   * Carica il DataSource prelevando le informazioni dal ResultSet passato
   * 
   * @param resultSet
   * @throws SQLException
   */
  public void loadFromResultSet(ResultSet resultSet) throws SQLException, IOException;

}
