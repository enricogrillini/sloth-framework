package it.eg.sloth.webdesktop.tag.form.toolbar;

import java.io.IOException;

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
public class EditableMasterDetailBarTag extends MasterDetailBarTag {

    private static final long serialVersionUID = 1L;

    @Override
    protected void endTag() throws IOException {
        if (getElement().getDataSource() == null) {
            return;
        }

        closeLeft();

        if (getElement().getDataSource() != null) {
            openRight();

            if (getForm().getPageInfo().getPageStatus().isClean()) {
                insertButton();
                deleteButton();
                updateButton();
            } else {
                commitButton();
                rollbackButton();
            }

            closeRight();
        }
    }

}
