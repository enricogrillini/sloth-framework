package it.eg.sloth.webdesktop.controller.common.tab;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.tabsheet.Tab;
import it.eg.sloth.form.tabsheet.TabSheet;
import it.eg.sloth.webdesktop.controller.common.FormPageInterface;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public interface BaseTabSelection<F extends Form> extends FormPageInterface<F> {

    default void execSelectTab(TabSheet tabSheet, Tab tab) throws Exception {
        tabSheet.setCurrentTabName(tab.getName());
    }

    default void onSelectTab(String tabSheetName, String tabName) throws Exception {
        TabSheet tabSheet = (TabSheet) getForm().getElement(tabSheetName);
        Tab tab = tabSheet.getElement(tabName);

        execSelectTab(tabSheet, tab);
    }

}
