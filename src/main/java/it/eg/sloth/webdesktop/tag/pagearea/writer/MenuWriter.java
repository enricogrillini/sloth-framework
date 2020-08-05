package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

public class MenuWriter extends HtmlWriter {

    public static final String openMenu() {

        StringBuilder result = new StringBuilder()
                .append("\n")
                .append("  <!-- Menu -->\n")
                .append("  <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">\n");

        if (!BaseFunction.isBlank(ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_LEFT))) {
            String logoLeft = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_LEFT);
            String logoRight = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_RIGHT);

            result
                    .append("   <span class=\"sidebar-brand d-flex align-items-center justify-content-center\">\n")
                    .append("    <div class=\"sidebar-brand-icon\"><img src=\"" + logoLeft + "\"></div>\n")
                    .append("    <div class=\"sidebar-brand-text mx-3\"><img src=\"" + logoRight + "\"></div>\n")
                    .append("   </span>\n")
                    .append("   <hr class=\"sidebar-divider\">\n");
        }

        return result.toString();
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
