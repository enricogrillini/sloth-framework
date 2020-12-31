package it.eg.sloth.webdesktop.tag.page.writer;

import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class PageWriter extends HtmlWriter {

    public static final String OPEN_HEAD = "<head>\n" +
            " <meta charset=\"utf-8\">\n" +
            " <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            " <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
            " <meta name=\"description\" content=\"\">\n" +
            " <meta name=\"author\" content=\"\">\n" +
            " <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"../img/favicon.png\">\n" +
            " <title>{0}</title>\n" +
            " <link href=\"../vendor/fontawesome/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
            " <link href=\"../css/sb-admin-2.css\" rel=\"stylesheet\">\n" +
            " <link href=\"../css/web-desktop.css\" rel=\"stylesheet\">\n";

    public static final String COPYRIGHT_INFO = "\n" +
            "         <hr>\n" +
            "         <div class=\"text-center small\">\n" +
            "          <p>\n" +
            "           <a href=\"https://github.com/enricogrillini/gilda-ce\" target=\"_blank\" rel=\"noopener noreferrer\" data-toggle=\"tooltip\" data-placement=\"bottom\" data-original-title=\"Project on GitHub\"><img src=\"../img/gilda.png\" alt=\"Project Gilda\"></a>&nbsp;Powered by:&nbsp;<a href=\"https://github.com/enricogrillini/gilda-ce\" target=\"_blank\" rel=\"noopener noreferrer\" data-toggle=\"tooltip\" data-placement=\"bottom\" data-original-title=\"Project on GitHub\">Gilda</a> - Version 2.0.0\n" +
            "          </p>\n" +
            "         </div>\n" +
            "\n" +
            "         <div class=\"text-center small\">\n" +
            "          <p>\n" +
            "           <a href=\"https://github.com/enricogrillini/gilda-ce/blob/master/LICENSE\" target=\"_blank\" rel=\"noopener noreferrer\" data-toggle=\"tooltip\" data-placement=\"bottom\" data-original-title=\"Licence\">Copyright (C) 2019-2020 Enrico Grillini</a>\n" +
            "          </p>\n" +
            "         </div>";

    public static final String CLOSE_HEAD = "</head>";

    private PageWriter() {
        // NOP
    }

    public static final String openHead(String title) {
        return MessageFormat.format(OPEN_HEAD, title);
    }

    public static final String closeHead() {
        return CLOSE_HEAD;
    }


    public static final String copyright() {
        return COPYRIGHT_INFO;

    }

}
