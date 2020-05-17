package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.search.model.SuggestionList;
import it.eg.sloth.webdesktop.search.model.suggestion.Suggestion;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Writer per la scrittura degli elementi di ricerca
 *
 * @author Enrico Grillini
 */
public class SearchWriter extends AbstractHtmlWriter {

    public static String writeOpen() {
        return new StringBuilder()
                .append("<!-- Search -->\n")
                .append("<div class=\"card shadow\">\n")
                .append(" <div class=\"card-body\">\n")
                .toString();
    }

    public static String writeClose() {
        return new StringBuilder()
                .append(" </div>\n")
                .append("</div>\n")
                .toString();
    }

    public static String writeSearchBar(String value) {
        return new StringBuilder()
                .append("<!-- Search bar -->\n")
                .append("<div class=\"input-group\">\n")
                .append(" <input id=\"searchValue\" name=\"searchValue\" type=\"hidden\">\n")
                .append(" <input id=\"searchData\" name=\"searchData\" type=\"hidden\">\n")
                .append(" <input id=\"searchText\" name=\"searchText\" type=\"text\" value=\"" + Casting.getHtml(value) + "\" class=\"form-control bg-light border-0 small\" placeholder=\"Search for...\" aria-label=\"Search\" aria-describedby=\"basic-addon2\">\n")
                .append(" <div class=\"input-group-append\">\n")
                .append("  <button class=\"btn btn-primary\" type=\"submit\">\n")
                .append("   <i class=\"fas fa-search fa-sm\"></i>\n")
                .append("  </button>\n")
                .append(" </div>\n")
                .append("</div>")
                .toString();
    }

    public static String writeInfo(SuggestionList suggestionList, Locale locale) throws BusinessException {
        return new StringBuilder()
                .append("<!-- Search info -->\n")
                .append("<br>\n")
                .append("<div class=\"d-flex flex-row-reverse border-top\">\n")
                .append(" <div class=\"small m-2\">" + DataTypes.INTEGER.formatText(BigDecimal.valueOf(suggestionList.getSuggestions().size()), locale) + " risultati trovati</div>\n")
                .append("</div>\n")
                .toString();
    }

    public static String writeSuggestions(SuggestionList suggestionList) {
        StringBuilder result = new StringBuilder()
                .append("<!-- Search result -->\n");

        int i = 0;
        for (Suggestion suggestion : suggestionList.getSuggestions()) {
            if (i >= suggestionList.getFirstPageRow() && i <= suggestionList.getLastPageRow()) {
                result.append(writeSuggestion(suggestion));

            } else if (i > suggestionList.getLastPageRow()) {
                break;
            }
            i++;
        }

        return result.toString();
    }


    public static String writeSuggestion(Suggestion suggestion) {
        StringBuilder result = new StringBuilder()
                .append("<div class=\"list-group-item list-group-item-action flex-column align-items-start border-0\">\n")
                .append(" <div class=\"d-flex flex-row\">\n");

        if (!BaseFunction.isBlank(suggestion.getImageUrl())) {
            result.append("  <span style=\"font-size: 2em\">" + suggestion.getImageUrl() + "</span>\n");
        }

        result
                .append("  <div class=\"ml-2\">\n")
                .append("   <a class=\"block\" href=\"" + suggestion.getUrl() + "\">\n")
                .append("    <h5 class=\"m-0\">" + Casting.getHtml(suggestion.getValue()) + "</h5>\n");

        if (!BaseFunction.isBlank(suggestion.getSubValue())) {
            result.append("    <div class=\"small\">" + Casting.getHtml(suggestion.getSubValue()) + "</div>\n");
        }

        result
                .append("   </a>\n")
                .append("  </div>\n")
                .append(" </div>\n")
                .append("</div>\n");

        return result.toString();


//            // Snippet
//            if (searchItem.hasSnippet()) {
//                writeln("  <div class=\"searchSnippet\">");
//
//                boolean first = true;
//                Iterator<SearchSnippet> iterator = searchItem.getSnippetIterator();
//                while (iterator.hasNext()) {
//                    SearchSnippet snippet = iterator.next();
//
//                    if (!first) {
//                        write(" - ");
//                    } else {
//                        first = false;
//                    }
//
//                    if (!BaseFunction.isBlank(snippet.getImgUrl())) {
//                        write("<img src=\"" + snippet.getImgUrl() + "\">");
//                    }
//
//                    if (!BaseFunction.isBlank(snippet.getTitle())) {
//                        write("<b>" + Casting.getHtml(snippet.getTitle()) + ": </b>");
//                    }
//
//                    if (!BaseFunction.isBlank(snippet.getValue())) {
//                        write("<span>" + Casting.getHtml(snippet.getValue()) + "</span>");
//                    }
//                }
//
//                writeln("  </div>");
//            }
//
//            // Descrizione
//            if (!BaseFunction.isBlank(searchItem.getDescription())) {
//                writeln("  <div class=\"searchDescription\">" + htmlDescription + "</div>");
//            }
//
//            // Funzioni
//            if (searchItem.hasFunction()) {
//                writeln("  <div class=\"searchFunction\">");
//
//                boolean first = true;
//                Iterator<SearchFunction> iterator = searchItem.getFunctionIterator();
//                while (iterator.hasNext()) {
//                    SearchFunction searchFunction = iterator.next();
//
//                    if (!first) {
//                        write(" ");
//                    } else {
//                        first = false;
//                    }
//
//                    String target = searchFunction.isExternal() ? " target=\"blank\"" : "";
//                    String name = Casting.getHtml(searchFunction.getName());
//
//                    if (!BaseFunction.isBlank(searchFunction.getHtml())) {
//                        write(searchFunction.getHtml());
//                    }
//
//                    write("<a href=\"" + searchFunction.getUrl() + "\"" + target + ">" + name + "</a>");
//                }

    }


}
