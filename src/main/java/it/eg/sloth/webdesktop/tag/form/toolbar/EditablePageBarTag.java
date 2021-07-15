package it.eg.sloth.webdesktop.tag.form.toolbar;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;

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
public class EditablePageBarTag extends WebDesktopTag<Form> {

    private static final long serialVersionUID = 1L;

    @Override
    public int startTag() throws IOException {
        writeln(ToolbarWriter.openLeft());

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        write(ToolbarWriter.closeLeft());
        write(ToolbarWriter.openRight());

        if (getForm().getPageInfo().getViewModality().equals(ViewModality.VIEW)) {
            write(ToolbarWriter.updateButton("", false, false));
        } else {
            write(ToolbarWriter.commitButton());
            write(ToolbarWriter.rollbackButton());
        }
        write(ToolbarWriter.closeRight());
    }

}
