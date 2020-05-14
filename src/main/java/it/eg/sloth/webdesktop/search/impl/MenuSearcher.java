package it.eg.sloth.webdesktop.search.impl;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.base.InMemorySearcher;
import it.eg.sloth.webdesktop.search.model.mapper.SearcSuggestionMapper;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;

import java.util.List;

public class MenuSearcher extends InMemorySearcher<Menu> {

    public MenuSearcher(Menu menu) {
        super();
        addMenu(menu);
    }

    private void addMenu(Menu menu) {
        for (Menu child : menu.getChilds().values()) {
            if (!BaseFunction.isBlank(child.getLink())) {
                add(child);
            }

            addMenu(child);
        }
    }

    @Override
    protected SimpleSuggestion simpleSuggestionFromObject(Menu menu) {
        SimpleSuggestion simpleSuggestion = new SimpleSuggestion();
        simpleSuggestion.setValue(menu.getShortDescription());
        simpleSuggestion.setSubValue(menu.getDescription());
        simpleSuggestion.setImageUrl(menu.getHtml());
        simpleSuggestion.setUrl(menu.getLink());
        simpleSuggestion.setSearchRelevance(SearchRelevance.MEDIUM);

        return simpleSuggestion;
    }

    @Override
    protected Suggestion suggestionFromObject(Menu menu) {
        return SearcSuggestionMapper.INSTANCE
                .simpleSuggestionToSuggestion(simpleSuggestionFromObject(menu));
    }

    @Override
    protected boolean match(Menu menu, List<String> words) {
        return StringUtil.containsAllWords(menu.getDescription() + " " + menu.getShortDescription(), words);
    }
}
