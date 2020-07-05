package it.eg.sloth.webdesktop.tag.form.tabsheet.writer;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

public class TabSheetWriter extends HtmlWriter {

    public static String tabsheet(TabSheet tabSheet, String lastController) {
        StringBuilder result = new StringBuilder()
                .append("\n")
                .append("<ul class=\"nav nav-tabs small\">\n");

        for (Tab tab : tabSheet) {
            if (!tab.isHidden()) {
                boolean current = tabSheet.getCurrentTab() == tab;

                result
                        .append(" <li class=\"nav-item\"><a")
                        .append(getAttribute("href", current, "#", lastController + "?" + NavigationConst.navStr(NavigationConst.TAB, tabSheet.getName(), tab.getName())))
                        .append(getAttribute(ATTR_CLASS, current, "nav-link active", "nav-link"))
                        .append(getAttributeTooltip(tab.getTooltip()))
                        .append(">" + Casting.getHtml(tab.getDescription()))
                        .append("</a></li>");
            }
        }

        return result
                .append("</ul>")
                .toString();
    }
}
