package it.eg.sloth.form.base;

import java.util.List;

public interface Elements<T extends Element> extends Element, Iterable<T> {

  public T getElement(String name);

  public void addChild(T element);

  public void removeChild(T element);

  public void removeChild(String name);

  public void removeChilds();

  public List<T> getElements();
}
