package it.eg.sloth.webdesktop.search;

import it.eg.sloth.TestFactory;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
@Slf4j
class SearchTest {

    @Test
    void searcherManagerTest() {
        SearchManager searchManager = TestFactory.getSearchManager();

        // Test: ricerca libera datatable
        List<SimpleSuggestion> simpleSuggestionList = searchManager.simpleSearch("Bob", 10);
        assertEquals(2, simpleSuggestionList.size());
        assertEquals("Clienti: Bob", simpleSuggestionList.get(0).getValue());
        assertEquals("Ordini: Ordine cliente Bob", simpleSuggestionList.get(1).getValue());

        List<Suggestion> suggestions = searchManager.search("Bob", 10);
        assertEquals(2, suggestions.size());

        // Test: ricerca libera menu
        simpleSuggestionList = searchManager.simpleSearch("Clienti", 10);
        assertEquals(2, simpleSuggestionList.size());
        assertEquals("Clienti", simpleSuggestionList.get(0).getValue());
        assertEquals("Tipo Clienti", simpleSuggestionList.get(1).getValue());

        suggestions = searchManager.search("Clienti", 10);
        assertEquals(2, suggestions.size());

        // Test: ricerca libera menu + datatable
        simpleSuggestionList = searchManager.simpleSearch("Tipo", 10);
        assertEquals(3, simpleSuggestionList.size());
        assertEquals("Tipo Clienti", simpleSuggestionList.get(0).getValue());
        assertEquals("Clienti: Alice", simpleSuggestionList.get(1).getValue());
        assertEquals("Clienti: Bob", simpleSuggestionList.get(2).getValue());

        suggestions = searchManager.search("Tipo", 10);
        assertEquals(3, suggestions.size());

        // Test: ricerca libera datatable con exactsearch
        simpleSuggestionList = searchManager.simpleSearch("cliente bob", 10);
        assertEquals(2, simpleSuggestionList.size());
        assertEquals("Clienti: Bob", simpleSuggestionList.get(0).getValue());
        assertEquals(SearchRelevance.VERY_HIGH, simpleSuggestionList.get(0).getSearchRelevance());
        assertEquals("Ordini: Ordine cliente Bob", simpleSuggestionList.get(1).getValue());
        assertEquals(SearchRelevance.MEDIUM, simpleSuggestionList.get(1).getSearchRelevance());

        suggestions = searchManager.search("cliente bob", 10);
        assertEquals(2, suggestions.size());
    }


    @Test
    void searcherManagerApplyTest() {
        SearchManager searchManager = TestFactory.getSearchManager();

        searchManager.applySearch("cliente bob", 10);

        assertEquals(2, searchManager.getSuggestionList().getSuggestions().size());
        assertEquals(0, searchManager.getSuggestionList().getCurrentPage());


        searchManager.applySearch("AAAAAAAAAA", 10);
    }
}
