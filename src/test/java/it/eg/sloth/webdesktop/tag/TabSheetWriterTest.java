package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.jaxb.form.BadgeType;
import it.eg.sloth.webdesktop.tag.form.tabsheet.writer.TabSheetWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
class TabSheetWriterTest {

    private static final String TAB_SHEET_VIEW = ResourceUtil.normalizedResourceAsString("snippet-html/tab-sheet/tab-sheet_view.html");
    private static final String TAB_SHEET_EDIT = ResourceUtil.normalizedResourceAsString("snippet-html/tab-sheet/tab-sheet_edit.html");

    private TabSheet tabSheet;

    @BeforeEach
    void init() {
        tabSheet = TabSheet.builder()
                .name("TabSheetProva")
                .build();

        Tab tab;
        tab = Tab.builder()
                .name("PrimoTab")
                .description("Primo Tab")
                .badgeHtml("-19.440")
                .badgeType(BadgeType.BADGE_DANGER)
                .build();

        tabSheet.addChild(tab);

        tab = Tab.builder()
                .name("SecondoTab")
                .description("Secondo Tab")
                .build();

        tabSheet.addChild(tab);
    }

    @Test
    void tabSheetViewTest() throws FrameworkException {
        assertEquals(TAB_SHEET_VIEW, TabSheetWriter.tabsheet(tabSheet, "page", ViewModality.VIEW));
    }

    @Test
    void tabSheetEditTest() throws FrameworkException {
        assertEquals(TAB_SHEET_EDIT, TabSheetWriter.tabsheet(tabSheet, "page", ViewModality.EDIT));
    }

}
