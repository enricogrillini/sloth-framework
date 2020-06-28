package it.eg.sloth.webdesktop.tag.form.tabsheet;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

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
 *
 * @author Enrico Grillini
 */
public class TabSheetTag extends BaseElementTag<TabSheet> {

  private static final long serialVersionUID = 1L;

  public int startTag() throws Throwable {
    TabSheet tabSheet = getElement();

    writeln("");
    writeln("<ul class=\"nav nav-tabs small\">");

    for (Tab tab : tabSheet) {
      if (!tab.isHidden()) {
        String descriptionHtml = Casting.getHtml(tab.getDescription());
        String hrefHtml = getWebDesktopDto().getLastController() + "?" + NavigationConst.navStr(NavigationConst.TAB, tabSheet.getName(), tab.getName());

        if (tabSheet.getCurrentTab() == tab) {
          // Tab corrente
          writeln(" <li class=\"nav-item\"><a class=\"nav-link active\" href=\"#\">" + descriptionHtml + "</a></li>");
        } else {
          // Tab generico
          writeln(" <li class=\"nav-item\"><a class=\"nav-link\" href=\"" + hrefHtml + "\">" + descriptionHtml + "</a></li>");
        }

      }
    }
    
    writeln("</ul>");

    return EVAL_BODY_INCLUDE;
  }

  protected void endTag() throws Throwable {
    // NOP
  }

}
