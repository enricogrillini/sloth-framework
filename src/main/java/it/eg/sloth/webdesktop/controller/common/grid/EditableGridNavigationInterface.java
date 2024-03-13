package it.eg.sloth.webdesktop.controller.common.grid;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.editable.FullEditingInterface;

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
 * <p>
 * Gestisce l'interfaccia per la navigazione su una griglia editabile
 *
 * @author Enrico Grillini
 */
public interface EditableGridNavigationInterface<F extends Form, G extends Grid<?>> extends BaseGridNavigationInterface<F, G>, FullEditingInterface<F> {

    default boolean execPreMove() throws Exception {
        if (ViewModality.VIEW.equals(getForm().getPageInfo().getViewModality())) {
            return true;
        } else {
            return execPostDetail(true);
        }
    }

    default void execPostMove() throws FrameworkException {
        getGrid().copyFromDataSource(getGrid().getDataSource());
    }

    default boolean execPostDetail(boolean validate) throws Exception {
        getGrid().post(getWebRequest());
        if (validate && getGrid().validate(getMessageList())) {
            getGrid().copyToDataSource(getGrid().getDataSource());
            return true;
        } else {
            return !validate;
        }
    }

    default boolean execInsert() throws Exception {
        if (ViewModality.VIEW.equals(getForm().getPageInfo().getViewModality()) || execPostDetail(true)) {
            getGrid().clearData();
            getGrid().getDataSource().add();
        }
        return true;
    }

    default boolean execUpdate() throws Exception {
        getGrid().copyFromDataSource(getGrid().getDataSource());
        return true;
    }

    default boolean execDelete() throws Exception {
        getGrid().getDataSource().remove();
        getGrid().copyFromDataSource(getGrid().getDataSource());
        return true;
    }

    default boolean execCommit() throws Exception {
        if (getGrid().size() <= 0 || execPostDetail(true)) {
            DataManager.saveFirstToLast(getForm());
            return true;
        } else {
            return false;
        }
    }

    default void onGoToRecord(int currentRow) throws Exception {
        if (execPreMove() && getGrid().getDataSource().size() > currentRow) {
            getGrid().getDataSource().setCurrentRow(currentRow);
            execPostMove();
        }
    }

    default void onFirstRow() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().first();
            execPostMove();
        }
    }

    default void onPrevPage() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().prevPage();
            execPostMove();
        }
    }

    default void onPrev() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().prev();
            execPostMove();
        }
    }

    default void onNext() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().next();
            execPostMove();
        }
    }

    default void onNextPage() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().nextPage();
            execPostMove();
        }
    }

    default void onLastRow() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().last();
            execPostMove();
        }
    }

}
