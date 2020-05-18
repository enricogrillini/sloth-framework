package it.eg.sloth.form.grid;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.MessageList;

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
 *
 * @author Enrico Grillini
 */
public class RadioGrid<D extends DataTable<?>> extends Grid<D> {


    public static final String LAST = "last";

    private int rowSelected;

    public RadioGrid(String name) {
        this(name, null, null, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public RadioGrid(String name, String description, String title, Boolean backButtonHidden, Boolean selectPageButtonHidden, Boolean firstButtonHidden, Boolean prevPageButtonHidden,
                     Boolean prevButtonHidden, Boolean detailButtonHidden, Boolean nextButtonHidden, Boolean nextPageButtonHidden, Boolean lastPageButtonHidden, Boolean insertButtonHidden,
                     Boolean deleteButtonHidden, Boolean updateButtonHidden, Boolean commitButtonHidden, Boolean rollbackButtonHidden) {
        super(name, description, title, backButtonHidden, selectPageButtonHidden, firstButtonHidden, prevPageButtonHidden, prevButtonHidden, detailButtonHidden, nextButtonHidden, nextPageButtonHidden, lastPageButtonHidden, insertButtonHidden, deleteButtonHidden, updateButtonHidden, commitButtonHidden, rollbackButtonHidden);

        this.rowSelected = 0;
    }

    public int getRowSelected() {
        return rowSelected;
    }

    public void setRowSelected(int rowSelected) {
        this.rowSelected = rowSelected;
    }

    public boolean isNewLine() {
        if (getDataSource() == null) {
            return false;
        }

        return getDataSource().size() == getRowSelected();
    }

    @Override
    public void post(WebRequest webRequest) {
        super.post(webRequest);

        if (webRequest.getString(getName()) != null) {
            if (LAST.equals(webRequest.getString(getName()))) {
                rowSelected = getDataSource().size();
            } else {
                rowSelected = Integer.valueOf(webRequest.getString(getName()));
            }
        }
    }

    @Override
    public boolean validate(MessageList messages) throws BusinessException {
        if (isNewLine()) {
            return super.validate(messages);
        } else {
            return true;
        }

    }

}
