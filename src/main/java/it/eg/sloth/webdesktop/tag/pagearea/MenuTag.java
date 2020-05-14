package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;

public class MenuTag extends WebDesktopTag {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    writeln("");
    writeln("  <!-- Menu -->");
    writeln("  <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">");
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

        for (Menu child : menu) {
          String childLinkHtml = child.getLink() == null ? "#" : Casting.getHtml(child.getLink());
          String childDescrizioneHtml = Casting.getHtml(child.getShortDescription());
          
          if (VoiceType.SEPARATOR == child.getVoiceType()) {
            writeln("   <hr class=\"sidebar-divider\">");
          } else if (VoiceType.TITLE == child.getVoiceType()) {
            writeln("   <h6 class=\"collapse-header\">" + childDescrizioneHtml + "</h6>");
          } else {
            writeln("      <a class=\"collapse-item\" href=\"" + childLinkHtml + "\">" + childDescrizioneHtml + "</a>");
          }
        }

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
  protected void endTag() throws Throwable {

    writeln("   <!-- Sidebar Toggler (Sidebar) -->");
    writeln("   <hr class=\"sidebar-divider d-none d-md-block\">");
    writeln("   <div class=\"text-center d-none d-md-inline\">");
    writeln("    <button class=\"rounded-circle border-0\" id=\"sidebarToggle\"></button>");
    writeln("   </div>");
    write("  </ul>");
  }

}
