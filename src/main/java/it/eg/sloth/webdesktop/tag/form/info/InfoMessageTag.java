package it.eg.sloth.webdesktop.tag.form.info;

import it.eg.sloth.webdesktop.tag.WebDesktopTag;

/**
 * 
 * Consente di scrivere in messaggio all'interno di una info box
 * 
 * @author Enrico Grillini
 * 
 */
public class InfoMessageTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int startTag() throws Throwable {
    writeln("<p style=\"margin-bottom: 5px\" class=\"noteRicevuta\">");
    writeln(" <span class=\"ui-icon ui-icon-info\" style=\"float: left; margin-right: .3em;\"></span>" + getMessage());
    writeln("</p>");

    write("<div class=\"frRow\">");
    return EVAL_BODY_INCLUDE;
  }

  protected void endTag() throws Throwable {
    write("</div>");
  }
}
