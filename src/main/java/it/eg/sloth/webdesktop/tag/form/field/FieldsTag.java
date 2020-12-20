package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.LabelControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

import java.io.IOException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Enrico Grillini
 */
public class FieldsTag<T extends Fields<?>> extends BaseElementTag<T> {

    private static final long serialVersionUID = 1L;

    @Override
    protected int startTag() throws IOException, FrameworkException {

        writeln("");
        for (SimpleField simpleField : getElement()) {
            write(GroupWriter.openRow());

            write(LabelControlWriter.writeLblControl(simpleField, getElement(), getViewModality(), null, "10cols"));

            write(GroupWriter.closeRow());
        }

        return SKIP_BODY;
    }

    @Override
    protected void endTag() {
        // NOP
    }

}
