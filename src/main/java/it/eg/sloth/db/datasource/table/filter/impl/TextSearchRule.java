package it.eg.sloth.db.datasource.table.filter.impl;

import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.table.filter.FilterRule;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.ToString;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@ToString
public class TextSearchRule implements FilterRule {

    private String text;
    private List<String> words;

    public TextSearchRule(String text) {
        setText(text);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        words = StringUtil.words(text);
    }

    private List<String> getWords() {
        return words;
    }

    @Override
    public boolean check(DataSource dataSource) {
        if (getWords().isEmpty()) {
            return true;
        }

        StringBuilder baseText = new StringBuilder();
        for (Object value : dataSource.values()) {
            if (value != null) {
                baseText.append(" " + value.toString());
            }
        }

        String match = baseText.toString().toLowerCase();

        for (String word : getWords()) {
            if (match.indexOf(word.toLowerCase()) > 0) {
                return false;
            }
        }

        return true;
    }
}
