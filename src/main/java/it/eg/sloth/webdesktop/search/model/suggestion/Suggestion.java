package it.eg.sloth.webdesktop.search.model.suggestion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.webdesktop.search.SearchRelevance;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Suggestion extends SimpleSuggestion {

    private String description;

    private List<SearchSnippet> snippetList;
    private List<SearchFunction> functionList;

    public Suggestion() {
        this(null, null, null, null, null, null);
    }

    public Suggestion(String value, String subValue, String imageUrl, String url, SearchRelevance itemRelevance, String description) {
        super(value, subValue, imageUrl, url, itemRelevance);

        this.description = description;
        snippetList = new ArrayList<>();
        functionList = new ArrayList<>();
    }

    public void clearSnippet() {
        snippetList.clear();
    }

    public void addSnippet(SearchSnippet searchSnippet) {
        snippetList.add(searchSnippet);
    }

    public void addSnippet(String imgUrl, String title, String value) {
        snippetList.add(new SearchSnippet(imgUrl, title, value));
    }

    public boolean hasSnippet() {
        return snippetList.size() > 0;
    }

    public Iterator<SearchSnippet> getSnippetIterator() {
        return snippetList.iterator();
    }

    public void clearFunction() {
        functionList.clear();
    }

    public void addFunction(SearchFunction searchFunction) {
        functionList.add(searchFunction);
    }

    public void addFunction(String name, String url, boolean external, String html) {
        functionList.add(new SearchFunction(name, url, external, html));
    }

    public boolean hasFunction() {
        return functionList.size() > 0;
    }

    public Iterator<SearchFunction> getFunctionIterator() {
        return functionList.iterator();
    }


}
