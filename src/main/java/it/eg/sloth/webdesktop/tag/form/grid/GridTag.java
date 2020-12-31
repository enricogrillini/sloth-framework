package it.eg.sloth.webdesktop.tag.form.grid;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.grid.writer.GridWriter;

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
public class GridTag extends AbstractGridTag<Grid<?>> {

    public int startTag() throws IOException, FrameworkException {
        if (getElement().getDataSource() != null) {
            writeln(GridWriter.openTable(getElement(), true, true, true));
            writeln(GridWriter.header(getElement(), getDetailFields(), true));
            writeln(GridWriter.rows(getElement(), getDetailFields(), getForm().getPageInfo().getViewModality(), true));

            if (getElement().hasTotalizer()) {
                writeln(GridWriter.total(getElement(), hasDetail()));
            }
        }

        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws IOException {
        if (getElement().getDataSource() != null) {
            writeln(GridWriter.closeTable());
        }
    }

}
