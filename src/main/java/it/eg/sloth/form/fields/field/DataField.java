package it.eg.sloth.form.fields.field;

import java.text.ParseException;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;

public interface DataField<T extends Object> extends SimpleField {

  /**
   * Ritorna l'alias
   * 
   * @return
   */
  public String getAlias();

  /**
   * Imposta l'alias
   * 
   * @param alias
   */
  public void setAlias(String alias);

  /**
   * Ritorna il data type
   * 
   * @return
   */
  public DataTypes getDataType();

  /**
   * Imposta il data type
   * 
   * @param dataType
   */
  public void setDataType(DataTypes dataType);

  /**
   * Ritorna il formato
   * 
   * @return
   */
  public String getFormat();

  /**
   * Imposta il formato
   * 
   * @param format
   */
  public void setFormat(String format);

  public String getData();

  public void setData(String data);

  public String escapeHtmlText();

  public String escapeJsText();

  public String escapeHtmlValue();

  public String escapeJsValue();

  /**
   * Imposta il testo del campo formattando il valore passato
   * 
   * @param value
   */
  public void setValue(T value) throws BusinessException;

  /**
   * Imposta il valore prelevandolo dal data source passato
   * 
   * @param dataSource
   */
  public void copyFromDataSource(DataSource dataSource) throws BusinessException;

  /**
   * Imposta il valore sul data source passato
   * 
   * @param dataSource
   */
  public void copyToDataSource(DataSource dataSource);

  /**
   * Ritorna il testo del campo nel formato nativo
   * 
   * @return
   * @throws ParseException
   */
  public T getValue();

  /**
   * 
   * @return
   */
  public boolean isValid();

  /**
   * Verifica la validita del testo passato
   *
   * @return
   */
  public Message check();

  /**
   * Effettua la validazione della request relativamente al campo
   * 
   * @param messages
   * @return
   */
  public boolean validate(MessageList messages) throws BusinessException;

}
