package it.eg.sloth.form;

import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
 *
 * @author Enrico Grillini
 */
public class TabSheetTest {

    @Test
    public void tabTest() {
        TabSheet tabSheet = new TabSheet("TabSheetName");

        Tab tab1 = Tab.builder()
                .name("Name1")
                .description("description1")
                .build();

        tabSheet.addChild(tab1);

        Tab tab2 = Tab.builder()
                .name("Name2")
                .description("description2")
                .build();

        tabSheet.addChild(tab2);


        assertEquals(tab1, tabSheet.getCurrentTab());
        //assertEquals("name", tabSheet.getCurrentTabName());

        tabSheet.setCurrentTabName("name2");
        assertEquals(tab2, tabSheet.getCurrentTab());

    }
}
