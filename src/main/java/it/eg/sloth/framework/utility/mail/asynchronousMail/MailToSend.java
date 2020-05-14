package it.eg.sloth.framework.utility.mail.asynchronousMail;

import java.sql.Timestamp;

import org.apache.commons.mail.HtmlEmail;

import it.eg.sloth.framework.common.base.TimeStampUtil;

public class MailToSend {
  private HtmlEmail htmlEmail;
  private Exception sendError;
  private Timestamp submitDate;
  private Timestamp lastTry;

  public MailToSend(HtmlEmail htmlEmail) {
    this.htmlEmail = htmlEmail;
    this.submitDate = TimeStampUtil.sysdate();
    this.sendError = null;
    this.lastTry = null;
  }

  public HtmlEmail getHtmlEmail() {
    return htmlEmail;
  }

  public void setHtmlEmail(HtmlEmail htmlEmail) {
    this.htmlEmail = htmlEmail;
  }

  public Exception getSendError() {
    return sendError;
  }

  public void setSendError(Exception sendError) {
    this.sendError = sendError;
  }

  public Timestamp getSubmitDate() {
    return submitDate;
  }

  public void setSubmitDate(Timestamp submitDate) {
    this.submitDate = submitDate;
  }

  public Timestamp getLastTry() {
    return lastTry;
  }

  public void setLastTry(Timestamp lastTry) {
    this.lastTry = lastTry;
  }

}
