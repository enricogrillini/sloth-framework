package it.eg.sloth.framework.monitor;

import java.util.GregorianCalendar;

import it.eg.sloth.framework.security.User;

public class MonitorEvent {
  private String group;
  private String name;
  private User user;
  private long start;
  private long end;

  public MonitorEvent(String group, String name, User user) {
    this.group = group;
    this.name = name;
    this.user = user;
    this.start = 0;
    this.end = 0;
  }

  public void start() {
    this.start = new GregorianCalendar().getTimeInMillis();
  }

  public void end() {
    this.end = new GregorianCalendar().getTimeInMillis();
  }

  /**
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the user
   */
  public User getUser() {
    return user;
  }

  /**
   * @return the start
   */
  public long getStart() {
    return start;
  }

  /**
   * @return the end
   */
  public long getEnd() {
    return end;
  }

}
