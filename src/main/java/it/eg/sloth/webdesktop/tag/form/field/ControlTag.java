package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import lombok.Getter;
import lombok.Setter;

/**
 * Tag per il disegno di una controllo
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class ControlTag extends BaseElementTag<SimpleField> {

    private static final long serialVersionUID = 1L;

    private String classname;
    private String style;

    @Override
    protected int startTag() throws Throwable {
        write(FormControlWriter.writeControl(getElement(), getParentElement(), getWebDesktopDto().getLastController(), getViewModality(), getClassname(), getStyle()));
        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
        // NOP
    }
}
