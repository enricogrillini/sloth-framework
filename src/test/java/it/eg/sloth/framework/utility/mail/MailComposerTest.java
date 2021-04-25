package it.eg.sloth.framework.utility.mail;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MailComposerTest {

    private static final String SIMPLE_MAIL = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            " <head>\n" +
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "  <title>Titolo</title>\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "</head>\n" +
            "<style>\n" +
            " body {color:black; font-family: Arial, Helvetica, sans-serif}\n" +
            "</style>\n" +
            "<body style=\"margin: 0; padding: 0;\">\n" +
            "<p>prova</p><p><b>prova</b></p></body>\n" +
            "</html>";

    private static final String PRETTY_MAIL = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
            "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
            " <head>\n" +
            "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "  <title>Titolo</title>\n" +
            "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
            "</head>\n" +
            "<style>\n" +
            " body {color:black; font-family: Arial, Helvetica, sans-serif}\n" +
            "</style>\n" +
            "<body style=\"margin: 0; padding: 0;\">\n" +
            " <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">  <tr><td style=\"padding: 0.2em\">prova</td></tr>  <tr><td style=\"padding: 0.2em\"><b>prova</b></td></tr> </table>\n" +
            "</body>\n" +
            "</html>";

    @Test
    void simpleHtmlMailComposerTest() {
        SimpleHtmlMailComposer simpleHtmlMailComposer = new SimpleHtmlMailComposer("Titolo");
        simpleHtmlMailComposer.addTextElement("prova");
        simpleHtmlMailComposer.addHtmlElement("<b>prova</b>");

        assertEquals(SIMPLE_MAIL, simpleHtmlMailComposer.getHtml());
    }

    @Test
    void prettyHtmlMailComposerTest() {
        PrettyHtmlMailComposer simpleHtmlMailComposer = new PrettyHtmlMailComposer("Titolo");
        simpleHtmlMailComposer.addTextElement("prova");
        simpleHtmlMailComposer.addHtmlElement("<b>prova</b>");

        assertEquals(PRETTY_MAIL, simpleHtmlMailComposer.getHtml());
    }


}
