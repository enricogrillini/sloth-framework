package it.eg.sloth.webdesktop.search.model;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;
import lombok.Setter;

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
@Setter
public class SuggestionList extends FrameComponent {

    String text;
    List<Suggestion> suggestions;
    int currentPage;
    int pageSize;

    public SuggestionList() {
        text = null;
        suggestions = new ArrayList<>();
        pageSize = 10;
    }

    public SuggestionList(String text, List<Suggestion> items) {
        this();
        this.text = text;
        load(items);
    }

    public void load(List<Suggestion> items) {
        this.suggestions = items;
        this.currentPage = 0;
    }

    public void setPageSize(int pageSize) {
        if (this.pageSize <= 0) {
            this.pageSize = Math.max(1, pageSize);
        }
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage < 0) {
            this.currentPage = 0;
        } else if (currentPage >= Math.ceil((double) suggestions.size() / (double) getPageSize())) {
            this.currentPage = (int) Math.ceil((double) suggestions.size() / (double) getPageSize()) - 1;
        } else {
            this.currentPage = currentPage;
        }
    }

    public int getFirstPageRow() {
        return getCurrentPage() * getPageSize();
    }

    public int getLastPageRow() {
        return Math.min((getCurrentPage() + 1) * getPageSize(), getSuggestions().size());
    }

}
