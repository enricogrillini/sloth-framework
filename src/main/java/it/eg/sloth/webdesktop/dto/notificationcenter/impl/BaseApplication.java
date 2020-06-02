package it.eg.sloth.webdesktop.dto.notificationcenter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationApplication;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationApplicationFunction;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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
@Getter()
@Setter
public class BaseApplication<T extends NotificationMessage> extends FrameComponent implements NotificationApplication<T>, Iterable<T> {

  String name;
  String title;
  String url;
  String subTitle1;
  String subTitle2;
  String description;
  boolean closeable;

  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  private Map<String, T> messages;

  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  private List<NotificationApplicationFunction> list;

  public BaseApplication(String name) {
    this(name, null, null, true);
  }

  public BaseApplication(String name, String title, String url, boolean closeable) {
    this.name = name;
    this.title = title;
    this.closeable = closeable;
    this.url = url;

    this.messages = new LinkedHashMap<>();
    this.list = new ArrayList<>();
  }

  @Override
  public void addFunction(NotificationApplicationFunction notificationApplicationFunction) {
    list.add(notificationApplicationFunction);
  }

  @Override
  public Iterator<NotificationApplicationFunction> functionIterator() {
    return list.iterator();
  }

  public int size() {
    return messages.size();
  }

  @Override
  public boolean isOpen() {
    return !messages.isEmpty();
  }

  @Override
  public void close() {
    messages.clear();
  }

  @Override
  public T getMessage(String id) {
    return messages.get(id);
  }

  @Override
  public T getMessage(int index) {
    return new ArrayList<>(messages.values()).get(index);
  }

  @Override
  public void removeMessage(int index) {
    messages.remove(getMessage(index).getId());
  }

  @Override
  public void removeMessage(String id) {
    messages.remove(id);
  }

  protected void addFirstMessage(T message) {
    Map<String, T> map = new LinkedHashMap<>();
    map.put(message.getId(), message);
    map.putAll(messages);

    messages = map;
  }

  protected void addMessage(T message) {
    messages.put(message.getId(), message);
  }

  @Override
  public Iterator<T> iterator() {
    return messages.values().iterator();
  }

}
