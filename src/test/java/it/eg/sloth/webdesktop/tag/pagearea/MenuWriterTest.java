package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.pagearea.writer.MenuWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
class MenuWriterTest {
    private static final String MENU = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/menu.html");
    private static final String MENU_SUBMENU = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/menu-submenu.html");
    private static final String MENU_LOGO = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/menu-logo.html");
    private static final String MENU_LOGO_URL = ResourceUtil.normalizedResourceAsString("snippet-html/pagearea/menu-logo-url.html");

    User user;

    @BeforeEach
    void init() {
        //  ConfigSingleton.getInstance().clearProperty();
        Menu submenu = new Menu("Prova3", VoiceType.VOICE, "Prova3", "Prova3", "", "")
                .addChild("SubProva1", VoiceType.VOICE, "SubProva1", "Prova1", "", "SubProva1Page.html")
                .addChild("SubProva2", VoiceType.VOICE, "SubProva2", "SubProva2", "", "SubProva2Page.html");

        Menu menu = new Menu()
                .addChild("Prova1", VoiceType.VOICE, "Prova1", "Prova1", "", "ProvaPage.html")
                .addChild("Separator", VoiceType.SEPARATOR, "Separator", "Separator", "", "")
                .addChild("Prova2", VoiceType.VOICE, "Prova2", "Prova2", "", "")
                .add(submenu);

        user = new User();
        user.setMenu(menu);
    }

    @AfterEach
    void finish() {
        ConfigSingleton.getInstance().clearProperty();
    }

    @Test
    void writeMenu() {
        assertEquals(MENU, MenuWriter.menu(user, "ProvaPage.html"));

        assertEquals(MENU_SUBMENU, MenuWriter.menu(user, "SubProva1Page.html"));

        ConfigSingleton.getInstance().addProperty(ConfigSingleton.FRAMEWORK_LOGO_LEFT, "logo-left.png");
        ConfigSingleton.getInstance().addProperty(ConfigSingleton.FRAMEWORK_LOGO_RIGHT, "logo-right.png");
        assertEquals(MENU_LOGO, MenuWriter.menu(user, "ProvaPage.html"));

        ConfigSingleton.getInstance().addProperty(ConfigSingleton.FRAMEWORK_LOGO_URL, "HomePage.html");
        assertEquals(MENU_LOGO_URL, MenuWriter.menu(user, "ProvaPage.html"));
    }

}
