package it.eg.sloth.db.decodemap;

import it.eg.sloth.framework.common.base.StringUtil;

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
public enum SearchType {
    EXACT, LIKE, IGNORE_CASE, MATCH;

    public boolean match(String value, String query) {
        if (value == null || query == null) {
            return false;
        }

        switch (this) {
            case EXACT:
                return value.equals(query);
            case IGNORE_CASE:
                return value.trim().equalsIgnoreCase(query.trim());
            case LIKE:
                return value.toLowerCase().contains(query.trim().toLowerCase());
            case MATCH:
            default:
                if (query.endsWith("*")) {
                    return StringUtil.patternMatch(value.toLowerCase(), query.toLowerCase());
                } else {
                    // Spaccheto la query in parole e verifico che siano tutte contenute nel "value"
                    return StringUtil.containsAllWords(value, StringUtil.words(query));
                }
        }
    }
}
