package it.eg.sloth.form;

import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.framework.pageinfo.PageInfo;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class Form extends AbstractElements<Element> {

  private PageInfo pageInfo;

  public Form(String title) {
    super("");
    this.pageInfo = new PageInfo(title);
  }
  
}
