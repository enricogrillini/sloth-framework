package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;

import java.io.IOException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class HtmlTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    @Override
    protected int startTag() throws IOException {
        writeln("<!DOCTYPE html>");
        writeln("<html lang=\"it\">");

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        writeln("</html>");
    }

}
