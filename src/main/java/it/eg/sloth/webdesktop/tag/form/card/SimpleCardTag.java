package it.eg.sloth.webdesktop.tag.form.card;

import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.card.writer.CardWriter;

/**
 * 
 * Tag per il disegno di una Card
 * 
 * @author Enrico Grillini
 * 
 */
public class SimpleCardTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    writeln("<!-- SimpleCard -->");
    writeln(CardWriter.openCard(BootStrapClass.BORDER_LEFT_NONE));

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln(CardWriter.closeCard());
  }

}
