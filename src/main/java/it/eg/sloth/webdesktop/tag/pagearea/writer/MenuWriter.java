package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

public class MenuWriter extends HtmlWriter {

    public static final String openMenu() {

        return new StringBuilder()
                .append("\n")
                .append("  <!-- Menu -->\n")
                .append("  <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">\n")
                .toString();

    }


    public static final String closeMenu() {
        return new StringBuilder()
                .append("   <!-- Sidebar Toggler (Sidebar) -->\n")
                .append("   <hr class=\"sidebar-divider d-none d-md-block\">\n")
                .append("   <div class=\"text-center d-none d-md-inline\">\n")
                .append("    <button class=\"rounded-circle border-0\" id=\"sidebarToggle\"></button>\n")
                .append("   </div>\n")
                .append("  </ul>\n")
                .toString();
    }
}
