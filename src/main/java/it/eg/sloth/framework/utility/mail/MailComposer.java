package it.eg.sloth.framework.utility.mail;

import it.eg.sloth.framework.utility.mail.element.HtmlElement;
import it.eg.sloth.framework.utility.mail.element.MailElement;
import it.eg.sloth.framework.utility.mail.element.TextElement;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class MailComposer {

    private String title;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected List<MailElement> elements;

    protected MailComposer(String title) {
        this.title = title;
        elements = new ArrayList<>();
    }

    public void clear() {
        elements.clear();
    }

    public void addTextElement(String text) {
        elements.add(new TextElement(text));
    }

    public void addHtmlElement(String html) {
        elements.add(new HtmlElement(html));
    }
}
