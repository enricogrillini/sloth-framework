package it.eg.sloth.webdesktop.tag.form.group;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
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
public class GroupTag extends WebDesktopTag<Form> {

  private static final long serialVersionUID = 1L;

  public static final String CLASS_NAME = "frGroup";

  private String legend;

  @Override
  public int startTag() throws IOException {
    writeln("");
    writeln("<fieldset>");
    if (!BaseFunction.isBlank(getLegend())) {
      String legendHtml = Casting.getHtml(getLegend());
      writeln(" <legend>" + legendHtml + "</legend>");
    }

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws IOException {
    writeln("</fieldset>");
  }

}
