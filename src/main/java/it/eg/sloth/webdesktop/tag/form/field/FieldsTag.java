package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

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
public class FieldsTag<T extends Fields<?>> extends BaseElementTag<T> {

    private static final long serialVersionUID = 1L;

    @Override
    protected int startTag() throws Throwable {

        writeln("");
        for (SimpleField simpleField : getElement()) {
            writeln("<div class=\"frRow\">");
            writeln(" " + GroupWriter.openCell(null));
            writeln("  " + FormControlWriter.writeLabel(simpleField));
            writeln(" " + GroupWriter.closeCell());
            writeln(" " + GroupWriter.openCell(null));
            writeln("  " + FormControlWriter.writeControl(simpleField, getElement(), getViewModality()));
            writeln(" " + GroupWriter.closeCell());
            writeln("</div>");
        }

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
        // NOP
    }

}
