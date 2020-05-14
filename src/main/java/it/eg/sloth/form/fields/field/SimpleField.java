package it.eg.sloth.form.fields.field;

import it.eg.sloth.form.WebRequest;
import it.eg.sloth.form.base.Element;

public interface SimpleField extends Element {
  
  
  public FieldType getFieldType();

  /**
   * Ritorna la descrizione
   * 
   * @return
   */
  public String getDescription();

  /**
   * Ritorna la descrizione in Html
   * 
   * @return
   */
  public String getHtmlDescription();

  /**
   * Ritorna la descrizione in Javascript notation
   * 
   * @return
   */
  public String getJsDescription();

  /**
   * Imposta la descrizione
   * 
   * @param description
   */
  public void setDescription(String description);

  /**
   * Ritorna il tooltip
   * 
   * @return
   */
  public String getTooltip();

  /**
   * Imposta il tooltip
   * 
   * @param tooltip
   */
  public void setTooltip(String tooltip);

  /**
   * Ritorna il tooltip in formato html
   * 
   * @return
   */
  public String getHtmlTooltip();

  /**
   * Ritorna il tooltip in formato js
   * 
   * @return
   */
  public String getJsTooltip();

  /**
   * Effettua il post della Web Request
   * 
   * @param webRequest
   * @return
   */
  public void post(WebRequest webRequest);

  /**
   * Effettua il post della Web Request contenente valori escaped tipicamente
   * usati con un post JSON
   * 
   * @param webRequest
   * @return
   */
  public void postEscaped(WebRequest webRequest, String encoding);
}
