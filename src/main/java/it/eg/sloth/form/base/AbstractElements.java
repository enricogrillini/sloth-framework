package it.eg.sloth.form.base;

import java.util.*;

public abstract class AbstractElements<T extends Element> extends AbstractElement implements Elements<T> {

    private Map<String, T> map;

    public AbstractElements(String name) {
        super(name);
        map = new LinkedHashMap<String, T>();
    }

    public void setLocale(Locale locale) {
        super.setLocale(locale);
        for (T element : map.values()) {
            element.setLocale(locale);
        }
    }

    @Override
    public void addChild(T element) {
        map.put(element.getName(), element);
    }

    @Override
    public void removeChild(T element) {
        map.remove(element.getName());
    }

    @Override
    public void removeChild(String name) {
        map.remove(name.toLowerCase());
    }

    @Override
    public void removeChilds() {
        map.clear();
    }

    @Override
    public T getElement(String name) {
        name = name.toLowerCase();
        int i = name.indexOf('.');

        if (i > 0) {
            T element = map.get(name.substring(0, i));

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
            return map.get(name);
        }
    }

    public Element getParentElement(String name) {
        name = name.toLowerCase();
        name = name.substring(0, name.lastIndexOf('.'));

        return getElement(name);
    }

    @Override
    public List<T> getElements() {
        return new ArrayList<T>(map.values());
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

}
