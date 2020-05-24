package it.eg.sloth.webdesktop.dto.notificationcenter;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationApplication;

import java.util.*;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
public class NotificationCenter extends FrameComponent implements Iterable<NotificationApplication<?>> {

  private LinkedHashMap<String, NotificationApplication<?>> notificationApps;
  private boolean show;

  public NotificationCenter() {
    notificationApps = new LinkedHashMap();
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
   * @param name
   * @return
   */
  public NotificationApplication getApplication(String name) {
    return notificationApps.get(name);
  }

  @Override
  public Iterator<NotificationApplication<?>> iterator() {
    return notificationApps.values().iterator();
  }

}
