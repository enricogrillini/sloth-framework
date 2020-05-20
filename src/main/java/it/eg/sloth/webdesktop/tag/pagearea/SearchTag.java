package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.pagearea.writer.SearchWriter;

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
public class SearchTag extends WebDesktopTag<Form> {

    @Override
    protected int startTag() throws Throwable {

        SuggestionList suggestionList = getWebDesktopDto().getSearchManager().getSuggestionList();

        // Open
        writeln(SearchWriter.writeOpen());

        // Barra di ricerca
        writeln(SearchWriter.writeSearchBar(suggestionList.getText()));

        // Info
        writeln(SearchWriter.writeInfo(suggestionList, getUser().getLocale()));

        // Lista risultati
        writeln(SearchWriter.writeSuggestions(getWebDesktopDto().getSearchManager().getSuggestionList()));

        // Barra di navigazione

        // Close
        writeln(SearchWriter.writeClose());

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
        // NOP
    }

}
