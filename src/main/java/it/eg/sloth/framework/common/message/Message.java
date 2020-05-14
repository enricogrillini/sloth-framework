package it.eg.sloth.framework.common.message;

public interface Message {

  /**
   * Ritorna la gravità degli errori
   * 
   * @return
   */
  public Level getSeverity();

  /**
   * Ritorna il testo del messaggio
   * 
   * @return
   */
  public String getDescription();

  /**
   * Ritorna la descrizione di secondo livello
   * 
   * @return
   */
  public String getSubDescription();

}
