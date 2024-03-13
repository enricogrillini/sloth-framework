package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.editable.SubEditingInterface;
import it.eg.sloth.webdesktop.controller.common.grid.BaseGridNavigationInterface;

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
 * Fornisce l'implementazione di una griglia EDITABILE master detail. Gestisce i
 * metodi: onInsert, onDelete, onUpdate, onCommit, onRollback
 *
 * @param <F>
 * @param <G>
 * @author Enrico Grillini
 */
public abstract class EditableMasterDetailPage<F extends Form, G extends Grid<?>> extends MasterDetailPage<F, G> implements SubEditingInterface<F>, BaseGridNavigationInterface<F, G> {

    protected EditableMasterDetailPage() {
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
                case NavigationConst.COMMIT:
                    onCommit();
                    return true;
                case NavigationConst.ROLLBACK:
                    onRollback();
                    return true;
                default:
                    // NOP
            }
        }

        if (navigation.length == 2) {
            Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
            switch (navigation[0]) {
                case NavigationConst.INSERT:
                    if (grid == getGrid()) {
                        onInsert();
                    } else {
                        onSubInsert(grid);
                    }
                    return true;

                case NavigationConst.DELETE:
                    if (grid == getGrid()) {
                        onDelete();
                    } else {
                        onSubDelete(grid);
                    }
                    return true;

                case NavigationConst.UPDATE:
                    onUpdate();
                    return true;
                default:
                    // NOP
            }
        }

        return false;
    }

    @Override
    public boolean execUpdate() throws Exception {
        execLoadDetail();
        execLoadSubDetail(null);
        return true;
    }

    @Override
    public boolean execInsert() throws Exception {
        getGrid().getDataSource().add();

        execLoadDetail();
        execLoadSubDetail(null);
        return true;
    }

    @Override
    public boolean execSubInsert(Grid<?> grid) throws Exception {
        execPostDetail(false);
        if (grid.size() > 0) {
            if (execPostSubDetail(grid, true)) {
                grid.getDataSource().add();
                execLoadSubDetail(grid);
            }
        } else {
            grid.getDataSource().add();
            execLoadSubDetail(grid);
        }

        return true;
    }

    @Override
    public boolean execDelete() throws Exception {
        if (getMessageList().isEmpty()) {
            getMessageList().setPopup(false);
        }
        getMessageList().addBaseWarning("Confermare la cancellazione del record corrente premendo il pulsante 'Salva'");

        execLoadDetail();
        getGrid().getDataSource().remove();

        return true;
    }

    @Override
    public boolean execSubDelete(Grid<?> grid) throws Exception {
        execPostDetail(false);
        grid.getDataSource().remove();

        if (grid.size() > 0) {
            execLoadSubDetail(grid);
        }

        return true;
    }

    @Override
    public boolean execCommit() throws Exception {
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DELETING) {
            DataManager.saveLastToFirst(getForm());
            return true;

        } else if (getForm().getPageInfo().getPageStatus() == PageStatus.INSERTING || getForm().getPageInfo().getPageStatus() == PageStatus.UPDATING) {
            boolean postResult = execPostDetail(true) && execPostSubDetail(null, true);
            if (postResult) {
                DataManager.saveFirstToLast(getForm());
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    @Override
    public boolean execRollback() throws Exception {
        DataManager.undo(getForm());
        return true;
    }

    @Override
    public void onInsert() throws Exception {
        if (!getForm().getPageInfo().getAccessibility().isCreate()) {
            navigationError("Inserimento non consentito");
        } else if (execInsert()) {
            getForm().getPageInfo().setPageStatus(PageStatus.INSERTING);
            getForm().getPageInfo().setViewModality(ViewModality.EDIT);
        }
    }

    @Override
    public void onSubInsert(Grid<?> grid) throws Exception {
        execSubInsert(grid);
    }

    @Override
    public void onDelete() throws Exception {
        if (!getForm().getPageInfo().getAccessibility().isDelete()) {
            navigationError("Cancellazione non consentita");
        } else if (execDelete()) {
            getForm().getPageInfo().setPageStatus(PageStatus.DELETING);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW);
        }
    }

    @Override
    public void onSubGoToRecord(Grid<?> grid, int row) throws Exception {
        if (getForm().getPageInfo().getViewModality() == ViewModality.EDIT) {
            execPostDetail(false);
            if (execPostSubDetail(grid, true)) {
                grid.getDataSource().setCurrentRow(row);
                execLoadSubDetail(grid);
            }
        } else {
            grid.getDataSource().setCurrentRow(row);
            execLoadSubDetail(grid);
        }
    }

    @Override
    public void onUpdate() throws Exception {
        if (!getForm().getPageInfo().getAccessibility().isUpdate()) {
            navigationError("Modifica non consentita");
        } else if (execUpdate()) {
            getForm().getPageInfo().setPageStatus(PageStatus.UPDATING);
            getForm().getPageInfo().setViewModality(ViewModality.EDIT);
        }
    }

    @Override
    public void onCommit() throws Exception {
        checkNavigation();

        if (execCommit()) {
            if (getGrid().getDataSource() == null || getGrid().getDataSource().size() == 0) {
                getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
            } else {
                getForm().getPageInfo().setPageStatus(PageStatus.DETAIL);
                execLoadDetail();
            }
            getForm().getPageInfo().setViewModality(ViewModality.VIEW);
        }
    }

    @Override
    public void onRollback() throws Exception {
        if (execRollback()) {
            if (getGrid().getDataSource() == null || getGrid().getDataSource().size() == 0) {
                getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
            } else {
                getForm().getPageInfo().setPageStatus(PageStatus.DETAIL);
                execLoadDetail();
            }
            getForm().getPageInfo().setViewModality(ViewModality.VIEW);
        }
    }

}
