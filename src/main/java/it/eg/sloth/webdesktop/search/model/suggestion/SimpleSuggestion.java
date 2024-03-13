package it.eg.sloth.webdesktop.search.model.suggestion;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import lombok.*;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSuggestion implements Comparable<SimpleSuggestion> {

    String value;
    String subValue;
    String imageUrl;
    String url;
    SearchRelevance searchRelevance;
    boolean valid;

    /**
     * La presentazione avviene rispettando il seguente ordine:
     * - Item relevance
     * - Ordine alfabetico
     *
     * @param searchSuggestion
     * @return
     */
    @Override
    public int compareTo(SimpleSuggestion searchSuggestion) {
        SearchRelevance thisRelevance = (SearchRelevance) BaseFunction.nvl(getSearchRelevance(), SearchRelevance.VERY_LOW);
        SearchRelevance otherRelevance = (SearchRelevance) BaseFunction.nvl(searchSuggestion.getSearchRelevance(), SearchRelevance.VERY_LOW);

        String thisValue = BaseFunction.nvl(getValue(), "");
        String otherValue = BaseFunction.nvl(searchSuggestion.getValue(), "");

        if (thisRelevance == otherRelevance) {
            return thisValue.compareTo(otherValue);
        } else {
            return -(thisRelevance.getLevel() - otherRelevance.getLevel());
        }
    }


}
