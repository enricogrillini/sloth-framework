package it.eg.sloth.db.query;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public interface SelectQueryInterface  {

  /**
   * Ritorna lo statement
   * 
   * @return
   */
  public String getStatement();

  /**
   * Imposta lo statement
   * 
   * @param statement
   */
  public void setStatement(String statement);

  /**
   * Ritorna una tabella contenente il risultato della query
   * 
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> selectTable() throws SQLException, IOException;

  /**
   * Ritorna una tabella contenente il risultato della query
   * 
   * @param connectionName
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> selectTable(String connectionName) throws SQLException, IOException;

  /**
   * Ritorna una tabella contenente il risultato della query
   * 
   * @param connection
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataTable<?> selectTable(Connection connection) throws SQLException, IOException;

  /**
   * Ritorna una riga contenente il risultato della query
   * 
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataRow selectRow() throws SQLException, IOException;

  /**
   * Ritorna una riga contenente il risultato della query
   * 
   * @param connectionName
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataRow selectRow(String connectionName) throws SQLException, IOException;

  /**
   * Ritorna una riga contenente il risultato della query
   * 
   * @param connection
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public DataRow selectRow(Connection connection) throws SQLException, IOException;

  /**
   * Popola la DataRow passata
   * 
   * @param dataRow
   * @param connectionName
   * @throws Throwable
   */
  public boolean populateDataRow(DataRow dataRow, String connectionName) throws SQLException, IOException;

  /**
   * Popola il DataTable passato
   * 
   * @param dataRow
   * @throws SQLException
   * @throws DataConnectionException
   */
  public boolean populateDataRow(DataRow dataRow) throws SQLException, IOException;

  /**
   * Popola il DataTable passato
   * 
   * @param dataRow
   * @param connection
   * @throws DataConnectionException
   * @throws SQLException
   */
  public boolean populateDataRow(DataRow dataRow, Connection connection) throws SQLException, IOException;

  /**
   * Popola il DataTable passato
   * 
   * @param dataTable
   * @param connectionName
   * @throws DataConnectionException
   * @throws SQLException
   */
  public void populateDataTable(DataTable<?> dataTable, String connectionName) throws SQLException, IOException;

  /**
   * Popola il DataTable passato
   * 
   * @param dataTable
   * @throws SQLException
   * @throws DataConnectionException
   */
  public void populateDataTable(DataTable<?> dataTable) throws SQLException, IOException;

  /**
   * Popola il DataTable passato
   * 
   * @param dataTable
   * @param connection
   * @throws DataConnectionException
   * @throws SQLException
   */
  public void populateDataTable(DataTable<?> dataTable, Connection connection) throws SQLException, IOException;

}
