package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.editable.BaseEditingInterface;

/**
 * Fornisce una prima implementazione di una Form editabile. Rispetto al
 * BaseController aggiunge: - aggiunge la gestione degli eventi onUpdate,
 * onCommit, onRollback
 *
 * @param <F>
 * @author Enrico Grillini
 */
public abstract class EditablePage<F extends Form> extends SimplePage<F> implements BaseEditingInterface {

    public EditablePage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();
        if (navigation.length == 1) {
            if (NavigationConst.UPDATE.equals(navigation[0])) {
                onUpdate();
                return true;
            } else if (NavigationConst.COMMIT.equals(navigation[0])) {
                onCommit();
                return true;
            } else if (NavigationConst.ROLLBACK.equals(navigation[0])) {
                onRollback();
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean execUpdate() throws Exception {
        return true;
    }

    @Override
    public boolean execCommit() throws Exception {
        if (execPostDetail(true)) {
            DataManager.saveFirstToLast(getForm());
            return true;
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
    public void onUpdate() throws Exception {
        if (execUpdate()) {
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    public void onCommit() throws Exception {
        if (execCommit()) {
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }

    public void onRollback() throws Exception {
        if (execRollback()) {
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }

}
