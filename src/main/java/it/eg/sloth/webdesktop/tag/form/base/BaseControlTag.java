package it.eg.sloth.webdesktop.tag.form.base;

import it.eg.sloth.form.fields.field.SimpleField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Enrico Grillini
 */
@Setter
@Getter
public abstract class BaseControlTag extends BaseElementTag<SimpleField> {

  private static final long serialVersionUID = 1L;

  /**
   * Scrive il controllo
   */
  protected abstract void writeField() throws Throwable;

  @Override
  public int startTag() throws Throwable {
    writeField();
    return SKIP_BODY;
  }

  @Override
  protected void endTag() throws Throwable {
  }

}
