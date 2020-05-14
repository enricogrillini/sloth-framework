package it.eg.sloth.webdesktop.search.base;

import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class InMemorySearcher<T> extends AbstractSearcher {

    List<T> searchSuggestionList;

    protected void add(T object) {
        searchSuggestionList.add(object);
    }

    protected abstract SimpleSuggestion simpleSuggestionFromObject(T object);

    protected abstract Suggestion suggestionFromObject(T object);

    protected abstract boolean match(T object, List<String> words);

    public InMemorySearcher() {
        super();
        searchSuggestionList = new ArrayList<>();
    }

    @Override
    public List<SimpleSuggestion> simpleSearch(String text) {
        boolean exactSearch = isExactSearch(text);
        List<String> words = getWords(text);

        List<SimpleSuggestion> result = new ArrayList<>();
        if (!words.isEmpty()) {
            for (T object : searchSuggestionList) {
                if (match(object, words)) {
                    SimpleSuggestion simpleSuggestion = simpleSuggestionFromObject(object);
                    if (exactSearch) {
                        simpleSuggestion.setSearchRelevance(SearchRelevance.VERY_HIGH);
                    }
                    result.add(simpleSuggestion);
                }
            }
        }

        return result;
    }

    @Override
    public List<Suggestion> search(String text) {
        boolean exactSearch = isExactSearch(text);
        List<String> words = getWords(text);

        List<Suggestion> result = new ArrayList<>();
        if (!words.isEmpty()) {
            for (T object : searchSuggestionList) {
                if (match(object, words)) {
                    Suggestion suggestion = suggestionFromObject(object);
                    if (exactSearch) {
                        suggestion.setSearchRelevance(SearchRelevance.VERY_HIGH);
                    }
                    result.add(suggestion);
                }
            }
        }

        return result;
    }
}
