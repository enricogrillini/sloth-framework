package it.eg.sloth.db.query.pagedquery;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.query.SelectQueryInterface;

/**
 * 
 * @author Enrico Grillini
 *
 */
public interface PagedQueryInterface extends SelectQueryInterface {

  /**
   * Ritorna una tabella conetenente il risultato della query
   * 
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> select(int start, int end) throws SQLException, IOException;

  /**
   * Ritorna una tabella conetenente il risultato della query
   * 
   * @param connectionName
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> select(String connectionName, int start, int end) throws SQLException, IOException;

  /**
   * Ritorna una tabella conetenente il risultato della query
   * 
   * @param connection
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> select(Connection connection, int start, int end) throws SQLException, IOException;

  /**
   * Ritorna il numero totale di righe
   * 
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public int getCount() throws SQLException, IOException;

  /**
   * Ritorna il numero totale di righe
   * 
   * @param connectionName
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public int getCount(String connectionName) throws SQLException, IOException;

  /**
   * Ritorna il numero totale di righe
   * 
   * @param connection
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public int getCount(Connection connection) throws SQLException, IOException;
}
