package it.eg.sloth.framework.utility.mail.element;

import it.eg.sloth.framework.common.casting.Casting;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TextElement implements MailElement {

    private String text;

    @Override
    public String getHtml() {
        return Casting.getHtml(text);
    }
}
