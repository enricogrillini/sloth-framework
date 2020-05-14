package it.eg.sloth.webdesktop.tag.form.card;

import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.webdesktop.tag.BootStrapClass;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.card.writer.CardWriter;

/**
 * 
 * Tag per il disegno di una Card
 * 
 * @author Enrico Grillini
 * 
 */
public class FieldCardTag extends BaseElementTag<TextField<?>> {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    writeln("<!-- SimpleCard -->");
    writeln(CardWriter.openCard(BootStrapClass.BORDER_LEFT_PRIMARY));
    writeln(CardWriter.fieldCardContent(getElement()));

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln(CardWriter.closeCard());
  }

}
