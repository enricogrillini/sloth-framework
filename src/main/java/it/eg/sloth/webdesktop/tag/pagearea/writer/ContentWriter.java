package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.alertcenter.model.Alert;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.util.Locale;

public class ContentWriter extends HtmlWriter {

    public static final String openBarRight() {
        return new StringBuilder()
                .append("<!-- Topbar - Right -->\n")
                .append("<ul class=\"navbar-nav ml-auto\">")
                .toString();
    }

    public static final String openAlertCenter(int size) {
        return new StringBuilder()
                .append(" <!-- Nav Item - Alerts Center -->\n")
                .append(" <li class=\"nav-item dropdown no-arrow mx-1\">\n")
                .append("  <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"alertsDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n")
                .append("   <i class=\"fas fa-bell fa-fw\" ></i><span class=\"badge badge-danger badge-counter\">" + size + "</span>\n")
                .append("  </a>\n")
                .append("  <div class=\"dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in\" aria-labelledby=\"alertsDropdown\">\n")
                .append("   <h6 class=\"dropdown-header\">Alerts Center</h6>\n")
                .toString();
    }

    public static final String writeAlert(Alert alert, Locale locale) throws FrameworkException {
        return new StringBuilder()
                .append("   <a class=\"dropdown-item d-flex align-items-center\"")
                .append(getAttribute("href", !BaseFunction.isBlank(alert.getHref()), alert.getHref()))
                .append(">\n")
                .append("    <div class=\"mr-3\">\n")
                .append("     <div class=\"icon-circle bg-" + alert.getType().name().toLowerCase() + "\">" + alert.getType().getIcon() + "</div>\n")
                .append("    </div>\n")
                .append("    <div>\n")
                .append("     <div class=\"small text-gray-500\">" + DataTypes.DATE.formatText(alert.getDate(), locale) + "</div>\n")
                .append("     <div class=\"font-weight-bold\">" + Casting.getHtml(alert.getText()) + "</div>\n")
                .append("     " + getElement("div", !BaseFunction.isBlank(alert.getDetail()), alert.getDetail()))
                .append("    </div>\n")
                .append("   </a>\n")
                .toString();
    }

    public static final String closeAlertCenter() {
        return new StringBuilder()
                .append("  </div>\n")
                .append(" </li>")
                .toString();
    }

}
