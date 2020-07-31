package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
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
public class SearchBarTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    public int startTag() throws IOException {

        writeln("");
        writeln("<!-- Search Bar -->");
        writeln("<div class=\"text-center\">");
        writeln(" <button name=\"" + NavigationConst.navStr(NavigationConst.LOAD) + "\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Cerca\"><i class=\"fas fa-search\"></i> Cerca</button>");
        writeln(" <button name=\"" + NavigationConst.navStr(NavigationConst.RESET) + "\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pulisci i filtri\"><i class=\"fas fa-broom\"></i> Pulisci</button>");

        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws IOException {
        writeln("</div>");
    }

}
