package it.eg.sloth.webdesktop.tag.form.base;

import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
public abstract class BaseContainerControlTag extends BaseControlTag {

    private static final long serialVersionUID = 1L;

    String controlWidth;
    String labelWidth;

    public void writeLabelContainer(SimpleField field) throws FrameworkException, IOException {
        if (field != null && FormControlWriter.hasLabel(field)) {
            write(GroupWriter.openCell(getLabelWidth()));
            write(FormControlWriter.writeLabel(field));
            write(GroupWriter.closeCell());
        } else {
            write(GroupWriter.openCell(getLabelWidth()));
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
            write(GroupWriter.closeCell());
        }
    }

    public void writeControlContainer(SimpleField field) throws FrameworkException, IOException {
        write(GroupWriter.openCell(getControlWidth()));
        if (field != null) {
            write(FormControlWriter.writeControl(field, getParentElement(), getViewModality()));
        } else {
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
        }
        write(GroupWriter.closeCell());
    }

}
