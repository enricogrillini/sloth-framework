package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.pagearea.writer.SearchWriter;

public class SearchTag extends WebDesktopTag {

    @Override
    protected int startTag() throws Throwable {

        SuggestionList suggestionList = getWebDesktopDto().getSearchManager().getSuggestionList();

        // Open
        writeln(SearchWriter.writeOpen());

        // Barra di ricerca
        writeln(SearchWriter.writeSearchBar(suggestionList.getText()));

        // Info
        writeln(SearchWriter.writeInfo(suggestionList, getUser().getLocale()));

        // Lista risultati
        writeln(SearchWriter.writeSuggestions(getWebDesktopDto().getSearchManager().getSuggestionList()));

        // Barra di navigazione

        // Close
        writeln(SearchWriter.writeClose());

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
    }

}
