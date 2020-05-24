package it.eg.sloth.framework.pageinfo;

import it.eg.sloth.framework.FrameComponent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Breadcrumbs extends FrameComponent implements Iterable<Breadcrumb> {

    private List<Breadcrumb> list;

    public Breadcrumbs() {
        list = new ArrayList<>();
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
        StringBuilder result = new StringBuilder();
        for (Breadcrumb title : list) {
            if (result.length() > 0) {
                result.append(" > ");
            }

            result.append(title.getText());
        }

        return result.toString();
    }

    public String getHtml() {
        StringBuilder result = new StringBuilder();
        for (Breadcrumb title : list) {
            if (result.length() > 0) {
                result.append(" > ");
            }

            result.append(title.getHtml());
        }

        return result.toString();
    }

}
