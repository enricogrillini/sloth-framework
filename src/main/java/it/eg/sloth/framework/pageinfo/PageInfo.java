package it.eg.sloth.framework.pageinfo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

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
@ToString(callSuper = true)
public class PageInfo {

    String title;
    String description;
    String keyWords;
    Breadcrumbs breadcrumbs;
    ViewModality viewModality;
    PageStatus pageStatus;
    Accessibility accessibility;

    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    Map<String, Object> objects;

    public PageInfo(String title) {
        this.title = title;
        this.breadcrumbs = new Breadcrumbs();
        this.viewModality = ViewModality.VIEW;
        this.pageStatus = PageStatus.MASTER;
        this.accessibility = new Accessibility();
        this.objects = new HashMap<>();
    }

    public void clearObjects() {
        objects.clear();
    }

    public void putObject(String key, Object value) {
        objects.put(key, value);
    }

    public Object getObject(String key) {
        return objects.get(key);
    }

    public boolean containsObject(String key) {
        return objects.containsKey(key);
    }

    public void setAccessibility(boolean create, boolean read, boolean update, boolean delete) {
        getAccessibility().setCreate(create);
        getAccessibility().setRead(read);
        getAccessibility().setUpdate(update);
        getAccessibility().setDelete(delete);
    }

}
