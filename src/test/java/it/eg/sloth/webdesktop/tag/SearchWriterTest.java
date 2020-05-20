package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.Locale;

import org.junit.Test;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.search.SearchManager;
import it.eg.sloth.webdesktop.search.SearchTest;
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
 *
 * @author Enrico Grillini
 *
 */
public class SearchWriterTest {

    private static final String SEARCH_BAR_TEMPLATE = "<!-- Search bar -->\n" +
            "<div class=\"input-group\">\n" +
            " <input id=\"searchValue\" name=\"searchValue\" type=\"hidden\">\n" +
            " <input id=\"searchData\" name=\"searchData\" type=\"hidden\">\n" +
            " <input id=\"searchText\" name=\"searchText\" type=\"text\" value=\"{0}\" class=\"form-control bg-light border-0 small\" placeholder=\"Search for...\" aria-label=\"Search\" aria-describedby=\"basic-addon2\">\n" +
            " <div class=\"input-group-append\">\n" +
            "  <button class=\"btn btn-primary\" type=\"submit\">\n" +
            "   <i class=\"fas fa-search fa-sm\"></i>\n" +
            "  </button>\n" +
            " </div>\n" +
            "</div>";

    private static final String INFO_TEMPLATE = "<!-- Search info -->\n" +
            "<br>\n" +
            "<div class=\"d-flex flex-row-reverse border-top\">\n" +
            " <div class=\"small m-2\">{0} risultati trovati</div>\n" +
            "</div>\n";


    private static final String CONTENT_TEMPLATE = "<!-- Search result -->\n" +
            "<div class=\"list-group-item list-group-item-action flex-column align-items-start border-0\">\n" +
            " <div class=\"d-flex flex-row\">\n" +
            "  <div class=\"ml-2\">\n" +
            "   <a class=\"block\" href=\"prova.html?id=1\">\n" +
            "    <h5 class=\"m-0\">Clienti: Bob</h5>\n" +
            "    <div class=\"small\">Bob the Builder - Tipo costrutture</div>\n" +
            "   </a>\n" +
            "  </div>\n" +
            " </div>\n" +
            "</div>\n" +
            "<div class=\"list-group-item list-group-item-action flex-column align-items-start border-0\">\n" +
            " <div class=\"d-flex flex-row\">\n" +
            "  <div class=\"ml-2\">\n" +
            "   <a class=\"block\" href=\"prova.html?id=1\">\n" +
            "    <h5 class=\"m-0\">Ordini: Ordine cliente Bob</h5>\n" +
            "    <div class=\"small\">5 prodotti - 1.000&euro;</div>\n" +
            "   </a>\n" +
            "  </div>\n" +
            " </div>\n" +
            "</div>\n";

    @Test
    public void fieldCardContentTest() throws FrameworkException {
        SearchManager searchManager = SearchTest.getSearchManager();
        searchManager.applySearch("cliente bob", 10);

        // SearchBar
        assertEquals(MessageFormat.format(SEARCH_BAR_TEMPLATE, "cliente bob"), SearchWriter.writeSearchBar("cliente bob"));

        // Info
        assertEquals(MessageFormat.format(INFO_TEMPLATE, "2"), SearchWriter.writeInfo(searchManager.getSuggestionList(), Locale.ITALY));

        // Lista risultati
        assertEquals(CONTENT_TEMPLATE, SearchWriter.writeSuggestions(searchManager.getSuggestionList()));
    }

}
