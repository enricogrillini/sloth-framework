package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class MenuWriter extends HtmlWriter {

    private static final String MENU_OPEN = "\n" +
            "  <!-- Menu -->\n" +
            "  <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordionSidebar\">\n";

    private static final String MENU_CLOSE = "" +
            "   <!-- Sidebar Toggler (Sidebar) -->\n" +
            "   <hr class=\"sidebar-divider d-none d-md-block\">\n" +
            "   <div class=\"text-center d-none d-md-inline\">\n" +
            "    <button class=\"rounded-circle border-0\" id=\"sidebarToggle\"></button>\n" +
            "   </div>\n" +
            "  </ul>\n";

    private static final String LOGO_NO_URL = "" +
            "   <div class=\"sidebar-brand d-flex justify-content-center\">\n" +
            "    <span class=\"sidebar-brand-icon\"><img src=\"{0}\"></span><span class=\"sidebar-brand-text\"><img src=\"{1}\"></span>\n" +
            "   </div>\n" +
            "   <hr class=\"sidebar-divider mt-2 mb-0\">\n";


    private static final String LOGO_URL = "" +
            "   <div class=\"sidebar-brand d-flex justify-content-center\">\n" +
            "    <a href=\"{0}\" class=\"sidebar-brand-icon\"><img src=\"{1}\"></a><a href=\"{0}\" class=\"sidebar-brand-text\"><img src=\"{2}\"></a>\n" +
            "   </div>\n" +
            "   <hr class=\"sidebar-divider mt-2 mb-0\">\n";

    public static final String openMenu() {
        StringBuilder result = new StringBuilder().append(MENU_OPEN);
        if (!BaseFunction.isBlank(ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_LEFT))) {
            String logoLeft = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_LEFT);
            String logoRight = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_RIGHT);
            String logoUrl = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_LOGO_URL);

            if (BaseFunction.isBlank(logoUrl)) {
                result.append(MessageFormat.format(LOGO_NO_URL, logoLeft, logoRight));
            } else {
                result.append(MessageFormat.format(LOGO_URL, logoUrl, logoLeft, logoRight));
            }
        }

        return result.toString();
    }

    public static final String closeMenu() {
        return MENU_CLOSE;
    }
}
