package it.eg.sloth.webdesktop.search.model.suggestion;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSuggestion implements Comparable<SimpleSuggestion> {

    private String value;
    private String subValue;
    private String imageUrl;
    private String url;
    private SearchRelevance searchRelevance;

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
