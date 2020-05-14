package it.eg.sloth.db.query;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Enrico Grillini
 *
 */
public interface ExecuteQueryInterface {
  
  /**
   * Restituisce lo statement sql
   * @return
   */
  public String getStatement();
  
  /**
   * Imposta lo statement sql
   * @param statement
   */
  public void setStatement(String statement);
  
  /**
   * Esegue un DDL o una DML
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public void execute () throws SQLException;
  
  /**
   * Esegue un DDL o una DML
   * @param connectionName
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public void execute (String connectionName) throws SQLException;
  
  /**
   * Esegue un DDL o una DML
   * @param connection
   * @return
   * @throws SQLException
   * @throws DataConnectionException
   */
  public void execute (Connection connection) throws SQLException;
  
  

}
