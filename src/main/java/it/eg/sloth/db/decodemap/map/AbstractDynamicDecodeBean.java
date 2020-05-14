package it.eg.sloth.db.decodemap.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.db.decodemap.value.TableDecodeValue;
import it.eg.sloth.framework.FrameComponent;

public abstract class AbstractDynamicDecodeBean<T, R extends DataRow> extends FrameComponent implements DecodeMap<T, TableDecodeValue<T, R>> {

  @Override
  public T encode(String description) {
    List<TableDecodeValue<T, R>> list = performSearch(description, MapSearchType.flexible, 2);

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
