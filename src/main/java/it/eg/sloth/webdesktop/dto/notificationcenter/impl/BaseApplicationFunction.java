package it.eg.sloth.webdesktop.dto.notificationcenter.impl;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.dto.notificationcenter.iface.NotificationApplicationFunction;

public class BaseApplicationFunction extends FrameComponent implements NotificationApplicationFunction {

  private String name;
  private String title;
  private String className;

  public BaseApplicationFunction(String name, String title, String className) {
    this.name = name;
    this.title = title;
    this.className = className;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getClassName() {
    return className;
  }

  @Override
  public void setClassName(String className) {
    this.className = className;
  }

}
