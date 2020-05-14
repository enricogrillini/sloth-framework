package it.eg.sloth.db.datasource;


public interface TransactionalDataRow extends TransactionalDataSource, DataRow {

  /**
   * Ritorna lo stato della riga
   * @return
   */
  public RowStatus getStatus();

  /**
   * Segnala che la riga è rimossa
   * @throws InvalidRemoveStateException
   */
  public void remove();

  /**
   * Salva le modifiche
   */
  public void save();

  /**
   * Annulla le modifiche
   */
  public void undo();

  /**
   * Forza lo status clean
   */
  public void forceClean();

}
