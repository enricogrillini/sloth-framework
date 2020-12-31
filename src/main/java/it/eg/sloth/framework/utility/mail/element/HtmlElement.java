package it.eg.sloth.framework.utility.mail.element;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HtmlElement implements MailElement {

    private String html;

}
