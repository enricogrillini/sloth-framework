package it.eg.sloth.webdesktop.tag.form.list;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.list.writer.ListWriter;

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
public class ListTag<T extends Grid<?>> extends BaseElementTag<T> {

    @Override
    protected int startTag() throws IOException, FrameworkException {
        Grid<?> grid = getElement();

        // Write Title
        writeln(ListWriter.writeTitle(grid));
        writeln(ListWriter.writeList(grid));

        return SKIP_BODY;
    }

    @Override
    protected void endTag() {
        // NOP
    }

}
