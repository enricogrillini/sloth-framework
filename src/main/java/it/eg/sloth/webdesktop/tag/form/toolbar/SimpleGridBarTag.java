package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

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
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class SimpleGridBarTag<E extends Grid<?>> extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    private String name = "";

    protected E getElement() {
        return (E) getForm().getElement(getName());
    }

    @Override
    public int startTag() throws IOException {
        write(ToolbarWriter.openLeft());
        write(ToolbarWriter.gridNavigationSimple(getElement(), getWebDesktopDto().getLastController(), getForm().getPageInfo().getPageStatus()));

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        write(ToolbarWriter.closeLeft());
        write(ToolbarWriter.openRight());
        write(ToolbarWriter.closeRight());
    }

}
