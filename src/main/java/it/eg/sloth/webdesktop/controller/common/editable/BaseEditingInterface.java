package it.eg.sloth.webdesktop.controller.common.editable;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.FormPageInterface;

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
 * <p>
 * Gestisce l'interfaccia di base per la gestione dell'editing (modifica e salvataggio)
 *
 * @author Enrico Grillini
 */
public interface BaseEditingInterface<F extends Form> extends FormPageInterface<F> {

    boolean execPostDetail(boolean validate) throws Exception;

    boolean execUpdate() throws Exception;

    boolean execCommit() throws Exception;

    boolean execRollback() throws Exception;

    default void onUpdate() throws Exception {
        if (execUpdate()) {
            getForm().getPageInfo().setPageStatus(PageStatus.UPDATING);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    default void onCommit() throws Exception {
        if (execCommit()) {
            getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }

    default void onRollback() throws Exception {
        if (execRollback()) {
            getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_VISUALIZZAZIONE);
        }
    }
}
