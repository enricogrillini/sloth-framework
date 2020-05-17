package it.eg.sloth.webdesktop.search.model.suggestion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.webdesktop.search.SearchRelevance;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
        return !snippetList.isEmpty();
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
        return !functionList.isEmpty();
    }

    public Iterator<SearchFunction> getFunctionIterator() {
        return functionList.iterator();
    }


}
