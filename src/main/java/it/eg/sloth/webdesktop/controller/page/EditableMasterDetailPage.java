package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.editable.FullEditingInterface;
import it.eg.sloth.webdesktop.controller.common.editable.SubEditingInterface;
import it.eg.sloth.webdesktop.controller.common.grid.BaseGridNavigationInterface;

/**
 * Fornisce l'implementazione di una griglia EDITABILE master detail. Gestisce i
 * metodi: onInsert, onDelete, onUpdate, onCommit, onRollback
 *
 * @param <F>
 * @param <G>
 * @author Enrico Grillini
 */
public abstract class EditableMasterDetailPage<F extends Form, G extends Grid<?>> extends MasterDetailPage<F, G> implements FullEditingInterface, SubEditingInterface, BaseGridNavigationInterface {

    public EditableMasterDetailPage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();

        if (navigation.length == 1) {
            if (NavigationConst.COMMIT.equals(navigation[0])) {
                onCommit();
                return true;
            } else if (NavigationConst.ROLLBACK.equals(navigation[0])) {
                onRollback();
                return true;
            }
        }

        if (navigation.length == 2) {
            Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);

            if (NavigationConst.INSERT.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onInsert();
                } else {
                    onSubInsert(grid);
                }
                return true;
            } else if (NavigationConst.DELETE.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onDelete();
                } else {
                    onSubDelete(grid);
                }
                return true;
            } else if (NavigationConst.UPDATE.equals(navigation[0])) {
                onUpdate();
                return true;
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
        if (execInsert()) {
            getForm().getPageInfo().setPageStatus(PageStatus.INSERTING);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    @Override
    public void onSubInsert(Grid<?> grid) throws Exception {
        execSubInsert(grid);
    }

    @Override
    public void onDelete() throws Exception {
        if (execDelete()) {
            getForm().getPageInfo().setPageStatus(PageStatus.DELETING);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }

    @Override
    public void onSubDelete(Grid<?> grid) throws Exception {
        execSubDelete(grid);
    }

    @Override
    public void onSubGoToRecord(Grid<?> grid, int record) throws Exception {
        if (getForm().getPageInfo().getViewModality() == ViewModality.VIEW_MODIFICA) {
            execPostDetail(false);
            if (execPostSubDetail(grid, true)) {
                grid.getDataSource().setCurrentRow(record);
                execLoadSubDetail(grid);
            }
        } else {
            grid.getDataSource().setCurrentRow(record);
            execLoadSubDetail(grid);
        }
    }

    @Override
    public void onUpdate() throws Exception {
        if (execUpdate()) {
            getForm().getPageInfo().setPageStatus(PageStatus.UPDATING);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    @Override
    public void onCommit() throws Exception {
        if (execCommit()) {
            if (getGrid().getDataSource() == null || getGrid().getDataSource().size() == 0) {
                getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
            } else {
                getForm().getPageInfo().setPageStatus(PageStatus.DETAIL);
                execLoadDetail();
            }
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
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
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }

}
