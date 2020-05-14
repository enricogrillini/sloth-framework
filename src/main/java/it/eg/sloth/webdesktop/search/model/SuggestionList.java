package it.eg.sloth.webdesktop.search.model;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
