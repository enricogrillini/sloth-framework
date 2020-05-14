package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.webdesktop.tag.form.base.BaseControlTag;
import lombok.Getter;
import lombok.Setter;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Stampa il testo di un controllo
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class TextMdTag extends BaseControlTag {

    private static final long serialVersionUID = 1L;

    @Override
    protected void writeField() throws Throwable {
        if (getElement() instanceof DataField ) {
            DataField<?> field = (DataField<?>) getElement();

            Parser parser = Parser.builder().build();
            Node document = parser.parse(field.getData());
            HtmlRenderer renderer = HtmlRenderer.builder().build();

            write(renderer.render(document));
        }
    }

}
