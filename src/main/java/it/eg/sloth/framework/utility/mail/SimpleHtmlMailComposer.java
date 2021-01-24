package it.eg.sloth.framework.utility.mail;

import it.eg.sloth.framework.utility.mail.element.MailElement;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
public class SimpleHtmlMailComposer extends MailComposer {

    private static final String OPEN_MAIL = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            " <head>\n" +
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "  <title>{0}</title>\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "</head>\n" +
            "<style>\n" +
            " body '{'color:black; font-family: Arial, Helvetica, sans-serif'}'\n" +
            "</style>\n" +
            "<body style=\"margin: 0; padding: 0;\">\n";

    private static final String CLOSE_MAIL = "</body>\n" +
            "</html>";

    private static final String OPEN_ROW = "<p>";

    private static final String CLOSE_ROW = "</p>";

    public SimpleHtmlMailComposer(String title) {
        super(title);
    }
    
    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append(MessageFormat.format(OPEN_MAIL, getTitle()));

        for (MailElement mailElement : elements) {
            builder
                    .append(OPEN_ROW)
                    .append(mailElement.getHtml())
                    .append(CLOSE_ROW);
        }

        builder.append(CLOSE_MAIL);

        return builder.toString();
    }

}
