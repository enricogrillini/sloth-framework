package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.controller.common.grid.ReportGridNavigationInterface;

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
 * Fornisce l'implementazione per la navigazione su una pagina di report
 *
 * @param <F>
 * @author Enrico Grillini
 */
public abstract class ReportGridPage<F extends Form> extends SimplePage<F> implements ReportGridNavigationInterface<F> {

    protected ReportGridPage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();
        if (navigation.length == 1) {
            switch (navigation[0]) {
                case NavigationConst.LOAD:
                    onLoad();
                    return true;
                case NavigationConst.RESET:
                    onReset();
                    return true;
                default:
                    // NOP
            }
        }

        if (navigation.length == 2) {
            Grid<?> grid;
            switch (navigation[0]) {
                case NavigationConst.FIRST_ROW:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onFirstRow(grid);
                    return true;

                case NavigationConst.PREV_PAGE:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onPrevPage(grid);
                    return true;

                case NavigationConst.NEXT_PAGE:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onNextPage(grid);
                    return true;

                case NavigationConst.LAST_ROW:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onLastRow(grid);
                    return true;

                default:
                    // NOP
            }
        }

        if (navigation.length == 3) {
            Grid<?> grid;
            switch (navigation[0]) {
                case NavigationConst.SORT_ASC:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onSort(grid, navigation[2], SortType.SORT_ASC_NULLS_LAST);
                    return true;

                case NavigationConst.SORT_DESC:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onSort(grid, navigation[2], SortType.SORT_DESC_NULLS_LAST);
                    return true;

                default:
                    // NOP
            }
        }

        return false;
    }



}
