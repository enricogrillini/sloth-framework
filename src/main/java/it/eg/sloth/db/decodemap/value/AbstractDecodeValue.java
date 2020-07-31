package it.eg.sloth.db.decodemap.value;

import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;

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
public abstract class AbstractDecodeValue<T> extends FrameComponent implements DecodeValue<T> {

  private boolean valid;

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }

  public boolean match(String matchString) {
    if (BaseFunction.isBlank(matchString)) {
      return false;
    }

    if (BaseFunction.isBlank(getDescription())) {
      return false;
    }

    return getDescription().toLowerCase().trim().indexOf(matchString.toLowerCase().trim()) >= 0;
  }

}
