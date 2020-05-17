package it.eg.sloth.db.decodemap.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.db.decodemap.value.TableDecodeValue;
import it.eg.sloth.framework.FrameComponent;

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
public abstract class AbstractDynamicDecodeBean<T, R extends DataRow> extends FrameComponent implements DecodeMap<T, TableDecodeValue<T, R>> {

  @Override
  public T encode(String description) {
    List<TableDecodeValue<T, R>> list = performSearch(description, MapSearchType.FLEXIBLE, 2);

    if (list.size() == 1) {
      return list.get(0).getCode();
    } else {
      return null;
    }
  }

  @Override
  public String decode(T code) {
    List<TableDecodeValue<T, R>> list = performSearch(code);

    if (list.size() == 1) {
      return list.get(0).getDescription();
    }

    return null;
  }

  @Override
  public boolean contains(T code) {
    List<TableDecodeValue<T, R>> list = performSearch(code);
    return list.size() == 1;
  }

  @Override
  public Iterator<TableDecodeValue<T, R>> iterator() {
    return new ArrayList<TableDecodeValue<T, R>>().iterator();
  }

  @Override
  public T getFirst() {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public void clear() {
    // NOP
  }

}
