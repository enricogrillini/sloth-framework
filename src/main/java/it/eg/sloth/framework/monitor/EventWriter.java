package it.eg.sloth.framework.monitor;


public interface EventWriter {

  /**
   * Rende persistente un evento
   * 
   * @param monitorEvent
   */
  public void writeEvent(MonitorEvent monitorEvent);
}
