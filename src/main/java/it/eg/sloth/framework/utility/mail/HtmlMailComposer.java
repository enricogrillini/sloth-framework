package it.eg.sloth.framework.utility.mail;

import it.eg.sloth.framework.utility.mail.element.MailElement;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class HtmlMailComposer {

    private static final String OPEN_MAIL = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            " <head>\n" +
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "  <title>{0}</title>\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "</head>\n" +
            "<style>\n" +
            " body {color:black; font-family: Arial, Helvetica, sans-serif}\n" +
            "</style>\n" +
            "<body style=\"margin: 0; padding: 0;\">\n" +
            " <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">";

    private static final String CLOSE_MAIL = " </table>\n" +
            "</body>\n" +
            "</html>";

    private static final String OPEN_ROW = "  <tr><td style=\"padding: 0.2em\">";

    private static final String CLOSE_ROW = "</td></tr>";

    private String title;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<MailElement> elements;

    public HtmlMailComposer(String title) {
        this.title = title;
        elements = new ArrayList<>();
    }

    public void clear() {
        elements.clear();
    }

    public String getHtml() {
        StringBuilder builder = new StringBuilder();
        builder.append(OPEN_MAIL);

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
