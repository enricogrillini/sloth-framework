package it.eg.sloth.webdesktop.initializer;

import it.eg.sloth.framework.model.Initializer;
import it.eg.sloth.framework.monitor.MonitorSingleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseInitializer implements Initializer {

  @Override
  public void execute() throws Exception {
    long eventid = 0;
    try {
      log.info("IN " + getClass().getName());
      eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.INITIALIZER, getClass().getName(), null);

      service();

    } catch (Exception e) {
      log.error("ERROR " + getClass().getName(), e);

    } finally {
      log.info("OUT " + getClass().getName());
      MonitorSingleton.getInstance().endEvent(eventid);
    }
  }

  public abstract void service() throws Exception;

}
