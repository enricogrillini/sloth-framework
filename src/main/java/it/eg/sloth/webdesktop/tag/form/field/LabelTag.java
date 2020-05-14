package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseControlTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * Tag per il disegno di una controllo
 * 
 * @author Enrico Grillini
 * 
 */
@Getter
@Setter
public class LabelTag extends BaseControlTag {

  private static final long serialVersionUID = 1L;

  String classname;
  String style;

  @Override
  protected void writeField() throws Throwable {
    SimpleField element = getElement();
    write(FormControlWriter.writeLabel(element, getViewModality(), getClassname(), getStyle()));
  }
}
