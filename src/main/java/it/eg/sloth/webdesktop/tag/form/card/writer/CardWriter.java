package it.eg.sloth.webdesktop.tag.form.card.writer;

import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;

/**
 * Collezione di metodi statici per la scrittura delle Card
 *
 * @author Enrico Grillini
 */
public class CardWriter extends AbstractHtmlWriter {

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
                .append("</div>\n")
                .toString();
    }

    public static final String fieldCardContent(TextField<?> field) {
        StringBuilder result = new StringBuilder()
                .append("   <div class=\"col mr-2\">\n")
                .append("    <div class=\"text-xs font-weight-bold text-primary text-uppercase mb-1\">" + field.getHtmlDescription() + "</div>\n")
                .append("    <div class=\"row no-gutters align-items-center\">\n")
                .append("     <div class=\"col-auto\">\n")
                .append("      <div class=\"h5 mb-0 mr-3 font-weight-bold text-gray-800\">" + field.escapeHtmlText() + "</div>\n")
                .append("     </div>\n")
                .append("     <!--div class=\"col\">\n")
                .append("      <div class=\"progress progress-sm mr-2\">\n")
                .append("       <div class=\"progress-bar bg-info\" role=\"progressbar\" style=\"width: 50%\"></div>\n")
                .append("      </div>\n")
                .append("     </div-->\n")
                .append("    </div>\n")
                .append("   </div>\n");

        if (!BaseFunction.isBlank(field.getBaseLink())) {
            result.append("   <a href=\"" + field.getBaseLink() + field.escapeHtmlValue() + "\" class=\"stretched-link\"></a>\n");
        }

        return result.toString();
    }


}
