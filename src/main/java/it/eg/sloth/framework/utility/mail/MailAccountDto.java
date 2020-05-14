package it.eg.sloth.framework.utility.mail;

public class MailAccountDto {

  private String mailServer;
  private int smtpPort;
  private boolean ssl;
  private boolean tls;
  private String mailUser;
  private String mailPassword;
  private String userName;

  public MailAccountDto(String mailServer, int smtpPort, boolean ssl, boolean tls, String mailUser, String mailPassword, String userName) {
    this.mailServer = mailServer;
    this.smtpPort = smtpPort;
    this.ssl = ssl;
    this.tls = tls;
    this.mailUser = mailUser;
    this.mailPassword = mailPassword;
    this.userName = userName;
  }

  public String getMailServer() {
    return mailServer;
  }

  public void setMailServer(String mailServer) {
    this.mailServer = mailServer;
  }

  public int getSmtpPort() {
    return smtpPort;
  }

  public void setSmtpPort(int smtpPort) {
    this.smtpPort = smtpPort;
  }

  public boolean isSsl() {
    return ssl;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  public boolean isTls() {
    return tls;
  }

  public void setTls(boolean tls) {
    this.tls = tls;
  }

  public String getMailUser() {
    return mailUser;
  }

  public void setMailUser(String mailUser) {
    this.mailUser = mailUser;
  }

  public String getMailPassword() {
    return mailPassword;
  }

  public void setMailPassword(String mailPassword) {
    this.mailPassword = mailPassword;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

}
