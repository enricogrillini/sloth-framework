package it.eg.sloth.db.datasource;

import java.sql.Connection;

public interface DbDataSource extends TransactionalDataSource {
  
  /**
   * Trasferisce le modifiche sul db in transazione
   * @param connection
   * @throws InvalidStateException
   * @throws TransactionException
   */
  public void post(Connection connection);

  /**
   * Riporta lostato del DataSource nelel condizioni pre post
   * @param connection
   * @throws InvalidStateException
   * @throws TransactionException
   */
  public void unPost();
  
  /**
   * Completa la transazione dopo aver trasferito le informazioni sul DB
   * @throws InvalidStateException
   */
  public void commit();

  /**
   * Annulla la transazione
   * @throws InvalidStateException
   */
  public void rollback();
}
