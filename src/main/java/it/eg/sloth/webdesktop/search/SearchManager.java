package it.eg.sloth.webdesktop.search;

import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
 *
 * @author Enrico Grillini
 */
public class SearchManager {

    List<List<Searcher>> searcherArray;

    @Getter
    private boolean empty;

    public SearchManager() {
        clean();
    }

    public void clean() {
        searcherArray = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            searcherArray.add(new ArrayList<>());
        }

        this.empty = true;
    }

    public void addSearcher(Searcher searcher, SearchRelevance relevance) {
        searcherArray.get(relevance.getLevel()).add(searcher);
        this.empty = false;
    }

    /**
     * Esegue una ricerca e ritorna una lista di SimpleSuggestion
     *
     * @param text
     * @param limits
     * @return
     */
    public List<SimpleSuggestion> simpleSearch(String text, int limits) {
        List<SimpleSuggestion> resultList = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            List<Searcher> searcherList = searcherArray.get(i);

            // Scorro i Searcher per cluster di rilevanza
            List<SimpleSuggestion> appSuggestionList = new ArrayList<>();
            for (Searcher searcher : searcherList) {
                appSuggestionList.addAll(searcher.simpleSearch(text));
            }

            // Ordino per cluster di rilevanza
            Collections.sort(appSuggestionList);

            // Aggiungo i risultati dell'intero cluster di rilevanza
            resultList.addAll(appSuggestionList);
            if (resultList.size() >= limits) {
                return resultList;
            }
        }

        return resultList;
    }

    /**
     * Esegue una ricerca e ritorna una lista di Suggestion
     *
     * @param text
     * @param limits
     * @return
     */
    public List<Suggestion> search(String text, int limits) {
        List<Suggestion> resultList = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            List<Searcher> searcherList = searcherArray.get(i);

            // Scorro i Searcher per cluster di rilevanza
            List<Suggestion> appSuggestionList = new ArrayList<>();
            for (Searcher searcher : searcherList) {
                List<Suggestion> list = searcher.search(text);
                appSuggestionList.addAll(list);
            }

            // Ordino per cluster di rilevanza
            Collections.sort(appSuggestionList);

            // Aggiungo i risultati dell'intero cluster di rilevanza
            resultList.addAll(appSuggestionList);
            if (resultList.size() >= limits) {
                return resultList;
            }
        }

        return resultList;
    }

    public SuggestionList searchAsSuggestionList(String text, int limits) {
        return new SuggestionList(text, search(text, limits));
    }

}
