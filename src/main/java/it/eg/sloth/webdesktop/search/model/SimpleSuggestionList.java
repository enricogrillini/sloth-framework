package it.eg.sloth.webdesktop.search.model;

import java.util.ArrayList;
import java.util.List;

import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import lombok.Data;

@Data
public class SimpleSuggestionList {

  private List<SimpleSuggestion> suggestions;

  public SimpleSuggestionList() {
    suggestions = new ArrayList<>();
  }

  public SimpleSuggestionList(List<SimpleSuggestion> simpleSuggestionList) {
    this();
    for (SimpleSuggestion simpleSuggestion : simpleSuggestionList) {
      this.suggestions.add(simpleSuggestion);
    }
  }
  
}
