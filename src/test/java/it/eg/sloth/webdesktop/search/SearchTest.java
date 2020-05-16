package it.eg.sloth.webdesktop.search;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.webdesktop.search.impl.DataTableSearcher;
import it.eg.sloth.webdesktop.search.impl.MenuSearcher;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;

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
public class SearchTest {

    private static MenuSearcher getMenuSearcher() {
        Menu menu = new Menu()
                .addChild("CLI", VoiceType.VOICE, "Clienti", "Gestione clienti", "immagine", "#")
                .addChild("ORD", VoiceType.VOICE, "Ordini", "Gestione ordini", "immagine", "#")
                .addChild("OFF", VoiceType.VOICE, "Offerte", "Gestione offerte", "immagine", "#")
                .addChild("DEC", VoiceType.VOICE, "Decodifiche", "Decodifiche", null, null);

        menu.getChilds().get("DEC").addChild("TIP", VoiceType.VOICE, "Tipo Clienti", "Tipo Clienti", null, "#");

        return new MenuSearcher(menu);
    }

    private static DataTableSearcher<Row> getClientiSearcher() {
        Table table = new Table();
        Row row = table.add();
        row.setBigDecimal("Id", BigDecimal.valueOf(1));
        row.setString("value", "Bob");
        row.setString("subValue", "Bob the Builder - Tipo costrutture");

        row = table.add();
        row.setBigDecimal("Id", BigDecimal.valueOf(2));
        row.setString("value", "Alice");
        row.setString("subValue", "Alice in wonderland - Tipo manager");

        DataTableSearcher<Row> dataTableSearcher = new DataTableSearcher<>("Clienti", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher.setKeyWords("Clienti", "Cliente", "Cli");
        dataTableSearcher.addData(table);

        return dataTableSearcher;
    }

    private static DataTableSearcher<Row> getOrdiniSearcher() {
        Table table = new Table();
        Row row = table.add();
        row.setBigDecimal("Id", BigDecimal.valueOf(1));
        row.setString("value", "Ordine cliente Bob");
        row.setString("subValue", "5 prodotti - 1.000€");

        row = table.add();
        row.setBigDecimal("Id", BigDecimal.valueOf(2));
        row.setString("value", "Ordine cliente Alice");
        row.setString("subValue", "4 prodotti - 500€");

        DataTableSearcher<Row> dataTableSearcher = new DataTableSearcher<>("Ordini", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher.setKeyWords("Ordini", "Ordine", "Ord");
        dataTableSearcher.addData(table);

        return dataTableSearcher;
    }

    public static SearchManager getSearchManager() {
        SearchManager searchManager = new SearchManager();
        searchManager.addSearcher(getMenuSearcher(), SearchRelevance.VERY_HIGH);
        searchManager.addSearcher(getClientiSearcher(), SearchRelevance.MEDIUM);
        searchManager.addSearcher(getOrdiniSearcher(), SearchRelevance.MEDIUM);

        return searchManager;
    }


    @Test
    public void searcherManagerTest() {
        SearchManager searchManager = getSearchManager();

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
    public void searcherManagerApplyTest() {
        SearchManager searchManager = getSearchManager();

        searchManager.applySearch("cliente bob", 10);

        assertEquals(2, searchManager.getSuggestionList().getSuggestions().size());
        assertEquals(0, searchManager.getSuggestionList().getCurrentPage());


        searchManager.applySearch("AAAAAAAAAA", 10);
    }
}
