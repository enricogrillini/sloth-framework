package it.eg.sloth.webdesktop.controller.common.editable;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.pageinfo.ViewModality;

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
 * Gestisce l'interfaccia completa per un'operazione CRUD
 *
 * @author Enrico Grillini
 */
public interface FullEditingInterface<F extends Form> extends BaseEditingInterface<F> {

    boolean execInsert() throws Exception;

    boolean execDelete() throws Exception;

    default void onInsert() throws Exception {
        if (execInsert()) {
            getForm().getPageInfo().setPageStatus(PageStatus.UPDATING);
            getForm().getPageInfo().setViewModality(ViewModality.EDIT);
        }
    }

    default void onDelete() throws Exception {
        if (execDelete()) {
            getForm().getPageInfo().setPageStatus(PageStatus.UPDATING);
            getForm().getPageInfo().setViewModality(ViewModality.EDIT);
        }
    }


}
