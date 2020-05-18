package it.eg.sloth.form.fields.field.base;

import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.casting.DataTypes;

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
public abstract class TextField<T extends Object> extends AbstractDataField<T> implements DataField<T> {

  private String baseLink;

  public TextField(String name, String description, String tooltip, DataTypes dataType) {
    this(name, name, description, tooltip, dataType, null, null);
  }

  public TextField(String name, String alias, String description, String tooltip, DataTypes dataType, String format, String baseLink) {
    super(name, alias, description, tooltip, dataType, format);
    this.baseLink = baseLink;
  }

  public String getBaseLink() {
    return baseLink;
  }

  public void setBaseLink(String baseLink) {
    this.baseLink = baseLink;
  }

}
