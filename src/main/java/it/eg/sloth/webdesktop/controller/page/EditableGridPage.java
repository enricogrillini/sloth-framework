package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.editable.FullEditingInterface;
import it.eg.sloth.webdesktop.controller.common.grid.EditableGridNavigationInterface;

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
 * Fornisce l'implementazione di una griglia editabile. Rispetto al
 * EditablePageController aggiunge: - aggiunge la gestione degli eventi
 * onInsert, onDelete, onGotoRecord
 *
 * @param <F>
 * @param <G>
 * @author Enrico Grillini
 */
public abstract class EditableGridPage<F extends Form, G extends Grid<?>> extends EditablePage<F> implements EditableGridNavigationInterface<F, G> {

    protected EditableGridPage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();

        if (navigation.length == 1) {
            if (NavigationConst.LOAD.equals(navigation[0])) {
                onLoad();
                return true;
            } else if (NavigationConst.RESET.equals(navigation[0])) {
                onReset();
                return true;
            }
        }

        if (navigation.length == 2) {
            if (NavigationConst.INSERT.equals(navigation[0])) {
                onInsert();
                return true;
            } else if (NavigationConst.DELETE.equals(navigation[0])) {
                onDelete();
                return true;
            } else if (NavigationConst.UPDATE.equals(navigation[0])) {
                onUpdate();
                return true;
            } else if (NavigationConst.FIRST_ROW.equals(navigation[0])) {
                onFirstRow();
                return true;
            } else if (NavigationConst.PREV_PAGE.equals(navigation[0])) {
                onPrevPage();
                return true;
            } else if (NavigationConst.PREV.equals(navigation[0])) {
                onPrev();
                return true;
            } else if (NavigationConst.NEXT.equals(navigation[0])) {
                onNext();
                return true;
            } else if (NavigationConst.NEXT_PAGE.equals(navigation[0])) {
                onNextPage();
                return true;
            } else if (NavigationConst.LAST_ROW.equals(navigation[0])) {
                onLastRow();
                return true;
            }
        }

        if (navigation.length == 3) {
            if (NavigationConst.ROW.equals(navigation[0])) {
                onGoToRecord(Integer.valueOf(navigation[2]));
                return true;
            } else if (NavigationConst.SORT_ASC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortType.SORT_ASC_NULLS_LAST);
                return true;
            } else if (NavigationConst.SORT_DESC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortType.SORT_DESC_NULLS_LAST);
                return true;
            }
        }

        return false;
    }

}
