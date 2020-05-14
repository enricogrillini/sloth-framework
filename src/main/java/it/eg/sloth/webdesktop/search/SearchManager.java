package it.eg.sloth.webdesktop.search;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchManager extends FrameComponent {

    List<List<Searcher>> searcherArray;

    @Getter
    @Setter
    SuggestionList suggestionList;

    public SearchManager() {
        clean();
    }

    public void clean() {
        searcherArray = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            searcherArray.add(new ArrayList<>());
        }

        this.suggestionList = new SuggestionList();
    }

    public void addSearcher(Searcher searcher, SearchRelevance relevance) {
        searcherArray.get(relevance.getLevel()).add(searcher);
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

    public void applySearch(String text, int limits) {
        setSuggestionList(new SuggestionList(text, search(text, limits)));
    }

}
