package it.eg.sloth.webdesktop.search.impl;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.base.InMemorySearcher;
import it.eg.sloth.webdesktop.search.model.mapper.SearcSuggestionMapper;
import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public class DataTableSearcher<T extends DataRow> extends InMemorySearcher<T> {

    String title;
    String keyField;
    String valueField;
    String subValueField;
    String baseLink;

    public DataTableSearcher(String title, String keyField, String valueField, String subValueField, String baseLink) {
        super();
        this.title = title;
        this.keyField = keyField;
        this.valueField = valueField;
        this.subValueField = subValueField;
        this.baseLink = baseLink;
    }

    public void addData(DataTable<T> table) {
        for (T row : table) {
            add(row);
        }
    }

    @Override
    protected SimpleSuggestion simpleSuggestionFromObject(T row) {
        SimpleSuggestion simpleSuggestion = new SimpleSuggestion();

        if (BaseFunction.isBlank(getTitle())) {
            simpleSuggestion.setValue(row.getString(getValueField()));
        } else {
            simpleSuggestion.setValue(getTitle() + ": " + row.getString(getValueField()));
        }


        if (!BaseFunction.isBlank(getSubValueField())) {
            simpleSuggestion.setSubValue(row.getString(getSubValueField()));
        }

        simpleSuggestion.setImageUrl(null);
        simpleSuggestion.setUrl(getBaseLink() + row.getObject(getKeyField()));
        simpleSuggestion.setSearchRelevance(SearchRelevance.MEDIUM);

        return simpleSuggestion;
    }

    @Override
    protected Suggestion suggestionFromObject(T row) {
        return SearcSuggestionMapper.INSTANCE
                .simpleSuggestionToSuggestion(simpleSuggestionFromObject(row));
    }

    @Override
    protected boolean match(T row, List<String> words) {
        String text = BaseFunction.nvl(row.getString(getValueField()), "");
        if (!BaseFunction.isBlank(getSubValueField())) {
            text += " " + BaseFunction.nvl(row.getString(getSubValueField()), "");
        }

        return StringUtil.containsAllWords(text, words);
    }
}
