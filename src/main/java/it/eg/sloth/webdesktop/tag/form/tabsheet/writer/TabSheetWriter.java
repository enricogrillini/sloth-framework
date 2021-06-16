package it.eg.sloth.webdesktop.tag.form.tabsheet.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class TabSheetWriter extends HtmlWriter {

    // TabSheet
    private static final String OPEN_TABSHEET = "\n" +
            "<!-- TabSheet -->\n" +
            "<ul class=\"nav nav-tabs small\">\n";

    private static final String TAB_VIEW = " <li class=\"nav-item\"><a{0}{1}{2}>{3}</a></li>\n";
    private static final String TAB_EDIT = " <li class=\"nav-item\"><span{0}{1}>{2}</span></li>\n";
    private static final String CLOSE_TABSHEET = "</ul>";

    // TabSheet List
    private static final String OPEN_TABSHEET_LIST = "\n" +
            "<!-- TabSheet -->\n" +
            "<div class=\"list-group list-group-flush\">\n";

    private static final String TAB_LIST_VIEW = " <span class=\"list-group-item\"><a{0}{1}>{2}</a></span>\n";
    private static final String CLOSE_TABSHEET_LIST = "</div>";


    public static String tabsheet(TabSheet tabSheet, String lastController, ViewModality viewModality) {
        StringBuilder result = new StringBuilder().append(OPEN_TABSHEET);

        for (Tab tab : tabSheet) {
            if (!tab.isHidden()) {
                boolean current = tabSheet.getCurrentTab() == tab;
                String description = Casting.getHtml(tab.getDescription());
                if (!BaseFunction.isBlank(tab.getBadgeHtml())) {
                    description += MessageFormat.format(" <span class=\"badge badge-pill {0}\">{1}</span>", tab.getBadgeType() == null ? "" : tab.getBadgeType().value(), tab.getBadgeHtml());
                }

                if (viewModality == ViewModality.VIEW_VISUALIZZAZIONE) {
                    result.append(MessageFormat.format(
                            TAB_VIEW,
                            getAttribute("href", current, "#", lastController + "?" + NavigationConst.navStr(NavigationConst.TAB, tabSheet.getName(), tab.getName())),
                            getAttribute(ATTR_CLASS, current, "nav-link active", "nav-link"),
                            getTooltipAttributes(tab.getTooltip()),
                            description));

                } else {
                    result.append(MessageFormat.format(
                            TAB_EDIT,
                            getAttribute(ATTR_CLASS, current, "nav-link active", "nav-link"),
                            getTooltipAttributes(tab.getTooltip()),
                            description));
                }
            }
        }

        return result.append(CLOSE_TABSHEET).toString();
    }


    public static String tabsheetList(TabSheet tabSheet, String lastController) {
        StringBuilder result = new StringBuilder().append(OPEN_TABSHEET_LIST);
        for (Tab tab : tabSheet) {
            if (!tab.isHidden()) {
                String description = Casting.getHtml(tab.getDescription());
                if (!BaseFunction.isBlank(tab.getBadgeHtml())) {
                    description += MessageFormat.format(" <span class=\"badge badge-pill {0}\">{1}</span>", tab.getBadgeType() == null ? "" : tab.getBadgeType().value(), tab.getBadgeHtml());
                }

                result.append(MessageFormat.format(
                        TAB_LIST_VIEW,
                        getAttribute("href", lastController + "?" + NavigationConst.navStr(NavigationConst.TAB, tabSheet.getName(), tab.getName())),
                        getTooltipAttributes(tab.getTooltip()),
                        description));
            }
        }

        return result.append(CLOSE_TABSHEET_LIST).toString();
    }
}
