package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class HeadTag extends WebDesktopTag<Form> {

    @Override
    protected int startTag() throws IOException {
        // setContentType
        pageContext.getResponse().setContentType("text/html; charset=UTF-8");
        pageContext.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());
        pageContext.getResponse().setLocale(Locale.ITALY);

        writeln("<head>");
        writeln(" <meta charset=\"utf-8\">");
        writeln(" <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">");
        writeln(" <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">");
        writeln(" <meta name=\"description\" content=\"\">");
        writeln(" <meta name=\"author\" content=\"\">");
        writeln("");

        writeln(" <link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"../img/favicon.png\">");

        // CSS
        writeln(" <link href=\"../vendor/fontawesome/css/all.min.css\" rel=\"stylesheet\" type=\"text/css\">");
        writeln(" <link href=\"../css/sb-admin-2.css\" rel=\"stylesheet\">");
        writeln(" <link href=\"../css/web-desktop.css\" rel=\"stylesheet\">");
        writeln("");

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        write("</head>");
    }

}
