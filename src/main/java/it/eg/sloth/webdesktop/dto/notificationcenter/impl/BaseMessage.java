package it.eg.sloth.webdesktop.dto.notificationcenter.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationMessage;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationMessageFunction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public abstract class BaseMessage extends FrameComponent implements NotificationMessage {

  String id;
  String title;
  String subTitle;
  String description;
  boolean closeable;
  String url;

  @Getter(AccessLevel.PRIVATE)
  @Setter(AccessLevel.PRIVATE)
  private List<NotificationMessageFunction> list;

  public BaseMessage(String id) {
    this(id, null, null, null, true, null);
  }

  public BaseMessage(String id, String title, String subTitle, String description, boolean closeable, String url) {
    this.id = id;
    this.title = title;
    this.subTitle = subTitle;
    this.description = description;
    this.closeable = closeable;
    this.url = url;

    list = new ArrayList<>();
  }

  @Override
  public void addFunction(NotificationMessageFunction notificationMessageFunction) {
    list.add(notificationMessageFunction);
  }

  @Override
  public Iterator<NotificationMessageFunction> iterator() {
    return list.iterator();
  }

}
