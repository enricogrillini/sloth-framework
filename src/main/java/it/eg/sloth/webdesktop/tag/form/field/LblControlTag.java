package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseContainerControlTag;

/**
 * 
 * Tag per il disegno di una controllo comprensivo di label e container
 * 
 * @author Enrico Grillini
 * 
 */
public class LblControlTag extends BaseContainerControlTag {

  private static final long serialVersionUID = 1L;

  protected void writeField() throws Throwable {
    AbstractSimpleField field = (AbstractSimpleField) getElement();
    writeLabelContainer(field);
    writeControlContainer(field);
  }
}
