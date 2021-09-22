package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.alertcenter.AlertsCenterSingleton;
import it.eg.sloth.webdesktop.alertcenter.model.Alert;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Locale;

public class ContentWriter extends HtmlWriter {

    public static final String ALERT_CARDS_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-open.html");
    public static final String ALERT_CARDS_ROW_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-row-open.html");
    public static final String ALERT_CARD = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-card.html");
    public static final String ALERT_CARDS_ROW_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/pagearea/alert-cards-row-close.html");

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

    public static String alertCenter(Locale locale) throws FrameworkException {
        Collection<Alert> alerts = AlertsCenterSingleton.getInstance().getList();
        if (!alerts.isEmpty()) {
            StringBuilder result = new StringBuilder();
            result.append(ContentWriter.openAlertCenter(alerts.size()));
            for (Alert alert : alerts) {
                result.append(ContentWriter.writeAlert(alert, locale));
            }
            result.append(ContentWriter.closeAlertCenter());

            return result.toString();
        } else {
            return StringUtil.EMPTY;
        }
    }

    public static String alertCards(Locale locale) throws FrameworkException {

        Collection<Alert> alerts = AlertsCenterSingleton.getInstance().getList();
        if (!alerts.isEmpty()) {
            StringBuilder result = new StringBuilder();

            int i = 0;
            for (Alert alert : alerts) {
                if (i == 0) {
                    result.append(ALERT_CARDS_OPEN);
                } else if (i % 2 == 0) {
                    result.append(ALERT_CARDS_ROW_CLOSE);
                    result.append(ALERT_CARDS_ROW_OPEN);
                }

                result.append(MessageFormat.format(
                        ALERT_CARD,
                        "<div class=\"icon-circle bg-" + alert.getType().name().toLowerCase() + "\">" + alert.getType().getIcon() + "</div>",
                        DataTypes.DATE.formatText(alert.getDate(), locale),
                        Casting.getHtml(alert.getText()),
                        Casting.getHtml(alert.getDetail()),
                        alerts.size() == 1 ? "col-12" : "col-6"));

                i++;
            }
            result.append(ALERT_CARDS_ROW_CLOSE);

            return result.toString();
        } else {
            return StringUtil.EMPTY;
        }
    }


}
