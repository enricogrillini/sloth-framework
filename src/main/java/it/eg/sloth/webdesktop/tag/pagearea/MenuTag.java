package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.pagearea.writer.EnvironmentWriter;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MenuWriter;

import java.io.IOException;

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
public class MenuTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    private void submenu(Menu menu) throws IOException {
        for (Menu child : menu) {
            String childLinkHtml = child.getLink() == null ? "#" : Casting.getHtml(child.getLink());
            String childDescrizioneHtml = Casting.getHtml(child.getShortDescription());

            if (VoiceType.SEPARATOR == child.getVoiceType()) {
                writeln("   <hr class=\"m-1 ml-4 mr-4\">");
            } else if (VoiceType.TITLE == child.getVoiceType()) {
                writeln("   <h6 class=\"collapse-header\">" + childDescrizioneHtml + "</h6>");
            } else {
                writeln("      <a class=\"collapse-item\" href=\"" + childLinkHtml + "\">" + childDescrizioneHtml + "</a>");
            }
        }

    }

    @Override
    protected int startTag() throws IOException {

        writeln(MenuWriter.openMenu());
        writeln(EnvironmentWriter.writeEnvironment(false));

        for (Menu menu : getWebDesktopDto().getUser().getMenu()) {
            String idHtml = Casting.getHtml(menu.getCode());
            String iconaHtml = menu.getHtml();
            String descrizioneHtml = Casting.getHtml(menu.getShortDescription());
            String linkHtml = Casting.getHtml(menu.getLink());

            if (VoiceType.SEPARATOR == menu.getVoiceType()) {
                writeln("   <hr class=\"sidebar-divider\">");
            } else if (VoiceType.TITLE == menu.getVoiceType()) {
                writeln("   <div class=\"sidebar-heading\">" + descrizioneHtml + "</div>");
            } else if (menu.hasChild()) {
                writeln("   <li class=\"nav-item\">");
                writeln("    <a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#" + idHtml + "\" aria-expanded=\"true\" aria-controls=\"collapseUtilities\">");
                writeln("     " + iconaHtml + "<span>" + descrizioneHtml + "</span>");
                writeln("    </a>");
                writeln("    <div id=\"" + idHtml + "\" class=\"collapse\" aria-labelledby=\"headingUtilities\" data-parent=\"#accordionSidebar\">");
                writeln("     <div class=\"bg-white py-2 collapse-inner rounded\">");

                submenu(menu);

                writeln("     </div>");
                writeln("    </div>");
                writeln("   </li>");

            } else {
                // Disegno una voce
                writeln("   <li class=\"nav-item\"><a class=\"nav-link\" href=\"" + linkHtml + "\">" + iconaHtml + "<span>" + descrizioneHtml + "</span></a></li>");
            }

        }

        return EVAL_BODY_INCLUDE;

    }

    @Override
    protected void endTag() throws IOException {
        write(MenuWriter.closeMenu());
    }

}
