package it.eg.sloth.webdesktop.dto.notificationcenter.iface;

import java.util.Iterator;

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
public interface NotificationApplication<T extends NotificationMessage> extends Iterable<T> {

  public String getName();

  public String getUrl();

  public String getTitle();

  public void setTitle(String title);

  public String getSubTitle1();

  public void setSubTitle1(String subTitle1);

  public String getSubTitle2();

  public void setSubTitle2(String subTitle2);

  public String getDescription();

  public void setDescription(String description);

  public void addFunction(NotificationApplicationFunction notificationApplicationFunction);

  public Iterator<NotificationApplicationFunction> functionIterator();

  public int size();

  public boolean isOpen();

  public void close();

  public boolean isCloseable();

  public void setCloseable(boolean closeable);

  public T getMessage(String id);

  public T getMessage(int index);

  public void removeMessage(String id);

  public void removeMessage(int index);

}
