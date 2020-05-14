package it.eg.sloth.framework.pageinfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.framework.FrameComponent;

public class Breadcrumbs extends FrameComponent implements Iterable<Breadcrumb> {

  private List<Breadcrumb> list;

  public Breadcrumbs() {
    list = new ArrayList<Breadcrumb>();
  }

  public void add(Breadcrumb breadcrumb) {
    list.add(breadcrumb);
  }

  public void add(String title, String hint, String link) {
    add(new Breadcrumb(title, hint, link));
  }

  public void add(String title) {
    add(title, null, null);
  }

  public int size() {
    return list.size();
  }

  public void clear() {
    list.clear();
  }

  @Override
  public Iterator<Breadcrumb> iterator() {
    return list.iterator();
  }

  public Breadcrumb getBreadcrumb(int i) {
    return list.get(i);
  }

  public String getText() {
    String result = "";

    for (Breadcrumb title : list) {
      if (!"".equals(result))
        result += " > ";

      result += title.getText();
    }

    return result;
  }

  public String getHtml() {
    String result = "";
    for (Breadcrumb title : list) {
      if (!"".equals(result))
        result += " > ";

      result += title.getHtml();
    }

    return result;
  }

}
