package it.eg.sloth.webdesktop.dto.notificationcenter;

import java.util.Iterator;
import java.util.LinkedHashMap;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationApplication;

public class NotificationCenter extends FrameComponent implements Iterable<NotificationApplication<?>> {

  private LinkedHashMap<String, NotificationApplication<?>> notificationApps;
  private boolean show;

  public NotificationCenter() {
    notificationApps = new LinkedHashMap<String, NotificationApplication<?>>();
  }

  public boolean isOpen() {
    return show;
  }

  public void open() {
    show = true;
  }

  public void close() {
    show = false;
  }

  /**
   * Rimuove tutte le applicazioni
   */
  public void clear() {
    notificationApps.clear();
  }

  /**
   * Aggiunge una applicazione al notification center
   * 
   * @param application
   * @return
   */
  public NotificationApplication<?> addApplication(NotificationApplication<?> application) {
    return notificationApps.put(application.getName(), application);
  }

  /**
   * Ritorna l'applicazione indicata
   * 
   * @param application
   * @return
   */
  public NotificationApplication<?> getApplication(String name) {
    return notificationApps.get(name);
  }

  @Override
  public Iterator<NotificationApplication<?>> iterator() {
    return notificationApps.values().iterator();
  }

}
