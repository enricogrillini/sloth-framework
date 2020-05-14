package it.eg.sloth.framework.common.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.Data;

/**
 * @author Enrico Grillini
 * 
 *         Gestisce la lista di errori applicativi
 */
@Data
public class MessageList {

  private List<Message> list;
  private boolean popup;

  /**
   * Costruttore
   */
  public MessageList() {
    list = new ArrayList<>();
    popup = true;
  }

  /**
   * Aggiunge un messaggio
   * 
   * @param message
   */
  public void add(Message message) {
    list.add(message);
  }

  /**
   * Aggiunge un informazione base
   * 
   * @param messaggio
   */
  public void addBaseInfo(String messaggio) {
    list.add(new BaseMessage(Level.INFO, messaggio, null));
  }

  /**
   * Aggiunge un informazione di successo
   * 
   * @param messaggio
   */
  public void addBaseSuccess(String messaggio) {
    list.add(new BaseMessage(Level.SUCCESS, messaggio, null));
  }

  /**
   * Aggiunge un warning base
   * 
   * @param messaggio
   */
  public void addBaseWarning(String messaggio) {
    list.add(new BaseMessage(Level.WARN, messaggio, null));
  }

  /**
   * Aggiunge un errore base
   * 
   * @param messaggio
   */
  public void addBaseError(String messaggio) {
    list.add(new BaseMessage(Level.ERROR, messaggio, null));
  }

  /**
   * Aggiunge un errore base
   * 
   * @param error
   */
  public void addBaseError(Exception error) {
    addBaseError("Errore: " + error.getMessage(), error);
  }

  /**
   * Aggiunge un errore base
   * 
   * @param messaggio
   */
  public void addBaseError(String messaggio, Throwable throwable) {
    list.add(new BaseMessage(Level.ERROR, messaggio, ExceptionUtils.getStackTrace(throwable)));
  }

  /**
   * Verifica se sono presenti messaggi nella lista
   * 
   * @return
   */
  public boolean isEmpty() {
    return list.isEmpty();
  }

  /**
   * Ritorna il più alto livello di segnalazione
   * 
   * @return
   */
  public Level getMaxSeverity() {
    Level level = Level.INFO;
    for (Message message : list) {
      if (message.getSeverity().hasHigerSeverity(level)) {
        level = message.getSeverity();
      }
    }

    return level;
  }

}
