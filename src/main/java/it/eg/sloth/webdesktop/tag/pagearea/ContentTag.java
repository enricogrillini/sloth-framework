package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.webdesktop.WebDesktopConstant;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.pagearea.writer.SearchWriter;
import lombok.Getter;
import lombok.Setter;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class ContentTag extends WebDesktopTag {

    private static final long serialVersionUID = 1L;

    private boolean multipart = false;
    private boolean hideSearch = false;

    @Override
    protected int startTag() throws Throwable {
        String titoloHtml = Casting.getHtml(getWebDesktopDto().getForm().getPageInfo().getTitle());
        String userHtml = Casting.getHtml(getUser().getName() + " " + getUser().getSurname());

        writeln();
        writeln("  <!-- Content Wrapper -->");
        writeln("  <div id=\"content-wrapper\" class=\"d-flex flex-column\">");
        writeln("   <div id=\"content\">");

        writeln("    <!-- Topbar -->");
        writeln("    <nav class=\"navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow\">");

        // Hamburger
        writeln("     <!-- Topbar - Sidebar Toggle -->");
        writeln("     <button id=\"sidebarToggleTop\" class=\"btn btn-link d-md-none rounded-circle mr-3\">");
        writeln("      <i class=\"fa fa-bars\"></i>");
        writeln("     </button>");
        writeln("");

        // Titolo pagina
        writeln("     <!-- Topbar - Titolo pagina -->");
        writeln("     <h3 class=\"navbar-text font-weight-bold col-2 col-sm-4\">" + titoloHtml + "</h3>");

        // Ricerca
        writeln("     <!-- Topbar - Search -->");
        writeln("     <form id=\"searchForm\" class=\"d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search\" action=\"" + WebDesktopConstant.Page.SEARCH_PAGE + "\" method=\"post\">");

        if (!isHideSearch()) {
            writeln(SearchWriter.writeSearchBar(null));
        }

        writeln("     </form>");

        // Utente
        writeln("     <!-- Topbar - User Information -->");
        writeln("     <ul class=\"navbar-nav ml-auto\">");

        // Help
        String documentationUrl = ConfigSingleton.getInstance().getProperty(ConfigSingleton.FRAMEWORK_DOCUMENTATION_URL);
        if (!BaseFunction.isBlank(documentationUrl)) {
            writeln("      <li class=\"nav-item dropdown no-arrow mx-1\"><a class=\"nav-link dropdown-toggle\" href=\"" + documentationUrl + "\" id=\"messagesDropdown\" role=\"button\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Documentazione e FAQ\" aria-haspopup=\"true\" aria-expanded=\"false\"><i class=\"fas fa-question-circle\"></i></a></li>");
        }

        // Separator
        writeln("      <div class=\"topbar-divider d-none d-sm-block\"></div>");

        writeln("      <li class=\"nav-item dropdown no-arrow\">");
        writeln("       <a class=\"nav-link dropdown-toggle\" href=\"#\" id=\"userDropdown\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">");
        writeln("        <span class=\"mr-2 d-none d-lg-inline text-gray-600 small\">" + userHtml + "</span>");

        if (getUser().hasAvatar()) {
            writeln("        <img class=\"img-profile rounded-circle\" src=\"../api/webdesktop/avatar\">");
        } else {
            writeln("        <div class=\"avatar-circle bg-primary\"><span class=\"initials\">" + getUser().getAvatarLetter() + "</span></div>");
        }

        writeln("       </a>");
        writeln("       <!-- Menu Utente-->");
        writeln("       <div class=\"dropdown-menu dropdown-menu-right shadow animated--grow-in\" aria-labelledby=\"userDropdown\">");

        for (Menu menu : getUser().getUserMenu().getChilds().values()) {

            switch (menu.getVoiceType()) {
                case VOICE:
                    writeln("        <a class=\"dropdown-item\" href=\"" + menu.getLink() + "\">");
                    if (!BaseFunction.isBlank(menu.getHtml())) {
                        writeln("         " + menu.getHtml());
                    }
                    writeln("         " + Casting.getHtml(menu.getShortDescription()));
                    writeln("        </a>");
                    break;

                case SEPARATOR:
                    writeln("        <div class=\"dropdown-divider\"></div>");
                    break;

                default:
                    break;
            }

        }

        writeln("        <a class=\"dropdown-item\" href=\"#\" data-toggle=\"modal\" data-target=\"#logoutModal\">");
        writeln("         <i class=\"fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400\"></i>");
        writeln("         Esci");
        writeln("        </a>");
        writeln("       </div>");
        writeln("      </li>");

        writeln("     </ul>");
        writeln("    </nav>");

        writeln("   <!-- Begin Page Content -->");
        writeln("   <div class=\"container-fluid\" >");

        writeln("    <form id=\"mainForm\" action=\"" + getWebDesktopDto().getLastController() + "\" method=\"post\"" + (isMultipart() ? " enctype=\"multipart/form-data\"" : "") + ">");

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws Throwable {
        writeln("    </form>");
        writeln("   </div>");
        writeln("  </div>");
        writeln(" </div>");
    }

}
