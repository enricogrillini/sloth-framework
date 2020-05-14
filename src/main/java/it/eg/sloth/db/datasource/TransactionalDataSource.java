package it.eg.sloth.db.datasource;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface TransactionalDataSource extends DataSource {

  public Object getOldObject(String name);

  public BigDecimal getOldBigDecimal(String name);

  public Timestamp getOldTimestamp(String name);

  public String getOldString(String name);
  
  public byte[] getOldByte(String name);

  /**
   * Salva le modifiche
   * @throws Throwable 
   * @throws SQLException
   * @throws DataConnectionException
   * @throws InvalidSaveStateException
   */
  public void save() throws Exception;

  /**
   * Annulla le modifiche
   * 
   * @throws InvalidStateException
   */
  public void undo();

  /**
   * Forza lo stato clean: dopo questa operazione il save e l'undo non hanno alcun effetto
   */
  public void forceClean();
}
