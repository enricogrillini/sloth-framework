package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.webdesktop.tag.form.base.BaseControlTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import lombok.Getter;
import lombok.Setter;

/**
 * Stampa il testo di un controllo
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class TextTag extends BaseControlTag {

    private static final long serialVersionUID = 1L;

    @Override
    protected void writeField() throws Throwable {
        write(TextControlWriter.writeControl(getElement()));
    }

}
