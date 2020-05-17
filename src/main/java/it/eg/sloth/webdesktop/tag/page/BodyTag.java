package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.view.AbstractTag;
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
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class BodyTag extends AbstractTag {

  static final long serialVersionUID = 1L;

  String className;

  @Override
  protected int startTag() throws Throwable {
    String classHtml = "";
    if (!BaseFunction.isBlank(getClassName())) {
      classHtml = " class=\"" + getClassName() + "\"";
    }

    writeln("<body id=\"page-top\"" + classHtml + ">");

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    writeln("</body>");
  }

}
