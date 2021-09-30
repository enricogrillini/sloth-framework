package it.eg.sloth.webdesktop.search.base;

import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
@Getter
public abstract class InMemorySearcher<T> extends AbstractSearcher {

    List<T> searchSuggestionList;

    protected void add(T object) {
        searchSuggestionList.add(object);
    }

    protected abstract SimpleSuggestion simpleSuggestionFromObject(T object);

    protected abstract Suggestion suggestionFromObject(T object);

    protected abstract boolean match(T object, List<String> words);

    protected InMemorySearcher() {
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
