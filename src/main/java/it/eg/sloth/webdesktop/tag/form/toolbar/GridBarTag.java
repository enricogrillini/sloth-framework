package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.grid.Grid;

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
public class GridBarTag extends AbstractGridToolBarTag<Grid<?>> {

    private static final long serialVersionUID = 1L;

    @Override
    public int startTag() throws IOException {
        DataTable<?> dataTable = getElement().getDataSource();
        if (dataTable == null) {
            return SKIP_BODY;
        }

        openLeft();

        // Pulsanti
        firstRowButton(true);
        prevPageButton(true);
        prevButton(true);
        nextButton(true);
        nextPageButton(true);
        lastRowButton(true);
        excelButton();

        // Informazioni di sintesi
        if (dataTable.size() > 0) {
            write(" Rec. " + (dataTable.getCurrentRow() + 1) + " di " + dataTable.size());
            if (dataTable.getPageSize() > 0) {
                write(", Pag. " + (dataTable.getCurrentPage() + 1) + " di " + dataTable.pages());
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        if (getElement().getDataSource() == null) {
            return;
        }

        closeLeft();
    }

}
