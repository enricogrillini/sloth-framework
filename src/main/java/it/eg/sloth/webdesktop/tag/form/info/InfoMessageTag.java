package it.eg.sloth.webdesktop.tag.form.info;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;

import java.io.IOException;

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
 *
 * @author Enrico Grillini
 */
public class InfoMessageTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int startTag() throws IOException {
        writeln("<p style=\"margin-bottom: 5px\" class=\"noteRicevuta\">");
        writeln(" <span class=\"ui-icon ui-icon-info\" style=\"float: left; margin-right: .3em;\"></span>" + getMessage());
        writeln("</p>");

        write("<div class=\"frRow\">");
        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws IOException {
        write("</div>");
    }
}
