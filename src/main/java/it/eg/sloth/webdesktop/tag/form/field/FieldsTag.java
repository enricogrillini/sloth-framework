package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

public class FieldsTag<T extends Fields<?>> extends BaseElementTag<T> {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {

    writeln("");
    for (SimpleField simpleField : getElement()) {
      writeln("<div class=\"frRow\">");
      writeln(" " + GroupWriter.openCell(null, null, null));
      writeln("  " + FormControlWriter.writeLabel(simpleField, getViewModality(), null, null));
      writeln(" " + GroupWriter.closeCell());
      writeln(" " + GroupWriter.openCell(null, null, null));
      writeln("  " + FormControlWriter.writeControl(simpleField, getElement(), getWebDesktopDto().getLastController(), getViewModality(), null, null));
      writeln(" " + GroupWriter.closeCell());
      writeln("</div>");
    }

    return SKIP_BODY;
  }

  @Override
  protected void endTag() throws Throwable {
    // NOP
  }

}
