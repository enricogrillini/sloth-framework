package it.eg.sloth.framework.pageinfo;

import lombok.ToString;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
@ToString(callSuper = true)
public class Breadcrumbs implements Iterable<Breadcrumb> {

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
