package it.eg.sloth.form.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.util.AntPathMatcher;

import java.util.*;

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
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class AbstractElements<T extends Element> implements Elements<T> {

    private String name;
    private Locale locale;
    private Map<String, T> map;

    public AbstractElements(String name) {
        this.name = name.toLowerCase();
    }

    public Map<String, T> getMap() {
        if (map == null) {
            map = new LinkedHashMap<>();
        }

        return map;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public Locale getLocale() {
        return this.locale == null ? Locale.getDefault() : this.locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
        for (T element : getMap().values()) {
            element.setLocale(locale);
        }
    }

    @Override
    public void addChild(T element) {
        getMap().put(element.getName(), element);
    }

    @Override
    public void removeChild(T element) {
        getMap().remove(element.getName());
    }

    @Override
    public void removeChild(String name) {
        getMap().remove(name.toLowerCase());
    }

    @Override
    public void removeChilds() {
        getMap().clear();
    }

    @Override
    public void removeChilds(String pattern) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        antPathMatcher.setCaseSensitive(false);

        map.entrySet().removeIf(e -> antPathMatcher.match(pattern, e.getValue().getName()));
    }


    @Override
    public T getElement(String name) {
        name = name.toLowerCase();
        int i = name.indexOf('.');

        if (i > 0) {
            T element = getMap().get(name.substring(0, i));

            if (element == null) {
                return null;
            } else if (element instanceof Elements) {
                @SuppressWarnings("unchecked")
                Elements<T> elements = (Elements<T>) element;
                return elements.getElement(name.substring(i + 1));
            } else {
                return null;
            }

        } else {
            return getMap().get(name);
        }
    }

    public Element getParentElement(String name) {
        name = name.toLowerCase();
        name = name.substring(0, name.lastIndexOf('.'));

        return getElement(name);
    }

    @Override
    public List<T> getElements() {
        return new ArrayList<>(getMap().values());
    }

    @Override
    public Iterator<T> iterator() {
        return getMap().values().iterator();
    }

}
