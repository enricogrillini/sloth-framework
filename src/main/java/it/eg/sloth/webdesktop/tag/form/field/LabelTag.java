package it.eg.sloth.webdesktop.tag.form.field;

import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.webdesktop.tag.form.base.BaseControlTag;
import it.eg.sloth.webdesktop.tag.form.field.writer.FormControlWriter;
import lombok.Getter;
import lombok.Setter;

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
public class LabelTag extends BaseControlTag {

  private static final long serialVersionUID = 1L;

  String classname;
  String style;

  @Override
  protected void writeField() throws Throwable {
    SimpleField element = getElement();
    write(FormControlWriter.writeLabel(element, getViewModality(), getClassname(), getStyle()));
  }
}
