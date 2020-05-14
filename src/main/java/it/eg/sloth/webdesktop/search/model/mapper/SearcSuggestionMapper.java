package it.eg.sloth.webdesktop.search.model.mapper;

import it.eg.sloth.webdesktop.search.model.suggestion.SimpleSuggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;

@Mapper
public interface SearcSuggestionMapper {

  SearcSuggestionMapper INSTANCE = Mappers.getMapper(SearcSuggestionMapper.class);

  Suggestion simpleSuggestionToSuggestion(SimpleSuggestion searchItem);

}
