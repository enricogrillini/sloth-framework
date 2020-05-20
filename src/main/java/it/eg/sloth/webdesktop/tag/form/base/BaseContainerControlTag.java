package it.eg.sloth.webdesktop.tag.form.base;

import it.eg.sloth.form.fields.field.base.AbstractSimpleField;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.text.ParseException;

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
    String controlStyle;
    String controlClassname;
    String labelWidth;
    String labelStyle;
    String labelClassname;

    public void writeLabelContainer(AbstractSimpleField field) throws ParseException, FrameworkException, IOException {
        if (field != null && FormControlWriter.hasLabel(field)) {
            write(GroupWriter.openCell(null, null, getLabelWidth()));
            write(FormControlWriter.writeLabel(field, getViewModality(), getControlClassname(), getControlStyle()));
            write(GroupWriter.closeCell());
        } else {
            write(GroupWriter.openCell(null, null, getLabelWidth()));
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
            write(GroupWriter.closeCell());
        }
    }

    public void writeControlContainer(AbstractSimpleField field) throws FrameworkException, IOException {
        write(GroupWriter.openCell(null, null, getControlWidth()));
        if (field != null) {
            write(FormControlWriter.writeControl(field, getParentElement(), getWebDesktopDto().getLastController(), getViewModality(), getControlClassname(), getControlStyle()));
        } else {
            write("<span class=\"form-control border-bottom-danger\">Campo " + getName() + " non trovato</span>");
        }
        write(GroupWriter.closeCell());
    }

}
