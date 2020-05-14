package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;

/**
 * Tag per il disegno di un gruppo dii campi
 * 
 * @author Enrico Grillini
 * 
 */
public class SearchBarTag extends WebDesktopTag<Form> {

  private static final long serialVersionUID = 1L;

  public int startTag() throws Throwable {

    writeln("");
    writeln("<!-- Search Bar -->");
    writeln("<div class=\"text-center\">");
    writeln(" <button name=\"" + NavigationConst.navStr(NavigationConst.LOAD) + "\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Cerca\"><i class=\"fas fa-search\"></i> Cerca</button>");
    writeln(" <button name=\"" + NavigationConst.navStr(NavigationConst.RESET) + "\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pulisci i filtri\"><i class=\"fas fa-broom\"></i> Pulisci</button>");

    // if (!BaseFunction.isBlank(getAdditionalButton())) {
    // writeln(ControlTag.Factory.writeControl((SimpleField) getForm().getElement(getAdditionalButton()), getForm().getParentElement(getAdditionalButton()), getWebDesktopDto().getLastController(), ViewModality.VIEW_MODIFICA, getAdditionalButtonClassName(), null, null, null));
    // }

    writeln("</div>");

    return SKIP_BODY;
  }

  protected void endTag() throws Throwable {

  }

}
