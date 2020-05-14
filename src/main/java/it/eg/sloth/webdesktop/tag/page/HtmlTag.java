package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.framework.view.AbstractTag;

public class HtmlTag extends AbstractTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    writeln("<!DOCTYPE html>");
    writeln("<html lang=\"it\">");
    
    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</html>");
  }

}
