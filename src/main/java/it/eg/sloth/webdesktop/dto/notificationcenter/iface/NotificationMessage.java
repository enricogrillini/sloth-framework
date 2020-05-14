package it.eg.sloth.webdesktop.dto.notificationcenter.iface;

public interface NotificationMessage extends Iterable<NotificationMessageFunction> {

  public String getId();

  public String getTitle();

  public void setTitle(String title);

  public String getSubTitle();

  public void setSubTitle(String subTitle);

  public String getDescription();

  public void setDescription(String description);

  public boolean isCloseable();

  public void setCloseable(boolean closeable);

  public String getUrl();

  public void setUrl(String url);

  public void addFunction(NotificationMessageFunction notificationMessageFunction);
}
