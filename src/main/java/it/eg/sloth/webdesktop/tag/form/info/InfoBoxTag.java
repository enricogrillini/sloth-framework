package it.eg.sloth.webdesktop.tag.form.info;

import it.eg.sloth.webdesktop.tag.WebDesktopTag;

/**
 * Tag per il disegno di un box di informazioni
 * 
 * @author Enrico Grillini
 * 
 */
public class InfoBoxTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  public static final String CLASS_NAME = "frGroup";

  private String title;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public int startTag() throws Throwable {
    writeln("<div class=\"ui-state-highlight ui-corner-all\" style=\"margin-top:10px; padding: 0.7em;\">");
    writeln(" <p style=\"margin-bottom: 5px\"><strong>" + getTitle() + "</strong></p>");

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</div>");
  }

}
