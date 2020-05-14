package it.eg.sloth.webdesktop.tag.form.tabsheet;

import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.tabSheet.Tab;
import it.eg.sloth.form.tabSheet.TabSheet;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

public class TabSheetTag extends BaseElementTag<TabSheet> {

  private static final long serialVersionUID = 1L;

  public int startTag() throws Throwable {
    TabSheet tabSheet = getElement();

    writeln("");
    writeln("<ul class=\"nav nav-tabs \">");

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
  }

}
