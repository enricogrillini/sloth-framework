package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class MenuWriter extends HtmlWriter {

    public static final String USG_MENU = "menu";
    public static final String USK_TOGGLE_MENU = "toggled";
    public static final String USV_TOGGLE_MENU = "true";

    private static final String MENU_OPEN = ResourceUtil.normalizedResourceAsString("snippet/pagearea/menu-open.html");
    private static final String MENU_CLOSE = ResourceUtil.normalizedResourceAsString("snippet/pagearea/menu-close.html");

    private static final String LOGO_URL = ResourceUtil.normalizedResourceAsString("snippet/pagearea/menu-logo-url.html");
    private static final String LOGO_NO_URL = ResourceUtil.normalizedResourceAsString("snippet/pagearea/menu-logo-no-url.html");

    private static final String SEPARATOR = "   <hr class=\"sidebar-divider\">\n";
    private static final String VOCE = "   <li class=\"nav-item{0}\"><a class=\"nav-link\" href=\"{1}\">{2}<span>{3}</span></a></li>\n";

    private static String submenu(Menu menu, String lastController) {
        StringBuilder result = new StringBuilder();
        for (Menu child : menu) {
            String childLinkHtml = child.getLink() == null ? "#" : Casting.getHtml(child.getLink());
            String childDescrizioneHtml = Casting.getHtml(child.getShortDescription());

            if (VoiceType.SEPARATOR == child.getVoiceType()) {
                result.append("   <hr class=\"m-1 ml-4 mr-4\">\n");
            } else if (VoiceType.TITLE == child.getVoiceType()) {
                result.append("   <h6 class=\"collapse-header\">" + childDescrizioneHtml + "</h6>\n");
            } else {

                if (child.isActive(lastController))
                    result.append("    <a class=\"collapse-item active\" href=\"" + childLinkHtml + "\">" + childDescrizioneHtml + "</a>\n");
                else
                    result.append("      <a class=\"collapse-item\" href=\"" + childLinkHtml + "\">" + childDescrizioneHtml + "</a>\n");
            }
        }

        return result.toString();
    }

    public static String menu(User user, String lastController) {
        boolean toggled = USV_TOGGLE_MENU.equalsIgnoreCase(user.getSetting(USG_MENU, USK_TOGGLE_MENU));

        StringBuilder result = new StringBuilder()
                .append(MenuWriter.openMenu(user))
                .append(EnvironmentWriter.writeEnvironment(false));

        for (Menu menu : user.getMenu()) {
            String idHtml = Casting.getHtml(menu.getCode());
            String iconaHtml = menu.getHtml();
            String descrizioneHtml = Casting.getHtml(menu.getShortDescription());
            String linkHtml = Casting.getHtml(menu.getLink());

            String activeHtml = menu.isActive(lastController) ? " active" : "";
            String showHtml = menu.isActive(lastController) && !toggled ? " show" : "";


            if (VoiceType.SEPARATOR == menu.getVoiceType()) {
                result.append(SEPARATOR);
            } else if (VoiceType.TITLE == menu.getVoiceType()) {
                result.append("   <div class=\"sidebar-heading\">" + descrizioneHtml + "</div>\n");
            } else if (menu.hasChild()) {
                result.append("   <li class=\"nav-item" + activeHtml + "\">\n")
                        .append("    <a class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#" + idHtml + "\" aria-expanded=\"true\" aria-controls=\"collapseUtilities\">\n")
                        .append("     " + iconaHtml + "<span>" + descrizioneHtml + "</span>\n")
                        .append("    </a>\n")
                        .append("    <div id=\"" + idHtml + "\" class=\"collapse" + showHtml + "\" aria-labelledby=\"headingUtilities\" data-parent=\"#accordionSidebar\">\n")
                        .append("     <div class=\"bg-white py-2 collapse-inner rounded\">\n")
                        .append(MenuWriter.submenu(menu, lastController))
                        .append("     </div>\n")
                        .append("    </div>\n")
                        .append("   </li>\n");

            } else {
                // Disegno una voce
                result.append(MessageFormat.format(VOCE, activeHtml, linkHtml, iconaHtml, descrizioneHtml));
            }

        }

        result.append(MENU_CLOSE);

        return result.toString();
    }


    private static final String openMenu(User user) {
        boolean toggled = USV_TOGGLE_MENU.equalsIgnoreCase(user.getSetting(USG_MENU, USK_TOGGLE_MENU));

        StringBuilder result = new StringBuilder().append(MessageFormat.format(MENU_OPEN, toggled ? "toggled" : ""));
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

}
