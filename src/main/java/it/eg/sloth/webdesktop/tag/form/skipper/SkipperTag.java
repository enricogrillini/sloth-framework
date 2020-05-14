package it.eg.sloth.webdesktop.tag.form.skipper;

import it.eg.sloth.form.skipper.Skipper;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

public class SkipperTag extends BaseElementTag<Skipper> {

  private static final long serialVersionUID = 1L;

  public int startTag() throws Throwable {
    if (getElement().isSkipBody()) {
      return SKIP_BODY;
    } else {
      return EVAL_BODY_INCLUDE;
    }
  }

  protected void endTag() throws Throwable {
  }

}
