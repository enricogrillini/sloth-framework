package it.eg.sloth.webdesktop.tag.form.card.writer;

import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

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
 * Collezione di metodi statici per la scrittura delle Card
 *
 * @author Enrico Grillini
 */
public class CardWriter extends HtmlWriter {

    private CardWriter() {
        // NOP
    }

    public static final String openCard(String borderLeft) {
        return new StringBuilder()
                .append("<div class=\"card shadow " + borderLeft + " py-0\">\n")
                .append(" <div class=\"card-body\">\n")
                .append("  <div class=\"row no-gutters align-items-center\">\n")
                .toString();
    }

    public static final String closeCard() {
        return new StringBuilder()
                .append("  </div>\n")
                .append(" </div>\n")
                .append("</div>")
                .toString();
    }

    public static final String fieldCardContent(TextField<?> field) {
        StringBuilder result = new StringBuilder()
                .append("   <div class=\"col mr-2\"")
                .append(getAttributeTooltip(field.getTooltip()))
                .append(">\n")
                .append("    <div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">" + field.getHtmlDescription() + CLOSE_DIV + "\n")
                .append("    <div class=\"row no-gutters align-items-center\">\n")
                .append("     <div class=\"col-auto\">\n")
                .append("      <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">" + field.escapeHtmlText() + CLOSE_DIV + "\n")
                .append("     </div>\n")
                .append("    </div>\n")
                .append("   </div>\n");

        if (!BaseFunction.isBlank(field.getBaseLink())) {
            result.append("   <a href=\"" + field.getBaseLink() + field.escapeHtmlValue() + "\" class=\"stretched-link\"></a>\n");
        }

        return result.toString();
    }


}
