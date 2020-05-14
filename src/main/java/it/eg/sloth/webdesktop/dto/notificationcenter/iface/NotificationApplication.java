package it.eg.sloth.webdesktop.dto.notificationcenter.iface;

import java.util.Iterator;

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
