package it.eg.sloth.webdesktop.search;

import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;

import java.util.List;
import java.util.Set;

public interface Searcher {

    public Set<String> getKeyWords();

    public List<SimpleSuggestion> simpleSearch(String text);

    public List<Suggestion> search(String text);

}
