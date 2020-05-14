package it.eg.sloth.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.query.SelectQueryInterface;

/**
 * @author Enrico Grillini
 * 
 *         Implementa la gestione di una riga
 * 
 */
public interface DataRow extends DataSource {

  /**
   * Inizializza la row dalla query
   * 
   * @param query
   * @throws SQLException
   */
  public void setFromQuery(SelectQueryInterface query);

  /**
   * Inizializza la row dalla query
   * 
   * @param query
   * @throws SQLException
   */
  public void setFromQuery(SelectQueryInterface query, Connection connection);

  /**
   * Carica la row dalla query
   * 
   * @param query
   * @throws SQLException
   */
  public boolean loadFromQuery(SelectQueryInterface query);

  /**
   * Carica la row dalla query
   * 
   * @param query
   * @param connection
   * @throws SQLException
   */
  public boolean loadFromQuery(SelectQueryInterface query, Connection connection);

}
