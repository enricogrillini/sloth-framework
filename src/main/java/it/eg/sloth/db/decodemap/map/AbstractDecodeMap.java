package it.eg.sloth.db.decodemap.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.MapSearchType;
import it.eg.sloth.db.decodemap.value.AbstractDecodeValue;
import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;

public abstract class AbstractDecodeMap<T, V extends AbstractDecodeValue<T>> extends FrameComponent implements DecodeMap<T, V> {

    private Map<T, V> map;

    public AbstractDecodeMap() {
        map = new LinkedHashMap<>();
    }

    public void put(V decodeValue) {
        map.put(decodeValue.getCode(), decodeValue);
    }

    public V get(T code) {
        return map.get(code);
    }

    @Override
    public T encode(String description) {
        List<V> list = performSearch(description, MapSearchType.flexible, 2);

        if (list.size() == 1) {
            return list.get(0).getCode();
        } else {
            return null;
        }
    }

    @Override
    public String decode(T code) {
        DecodeValue<T> decodeValue = get(code);

        if (decodeValue == null) {
            return null;
        } else {
            return decodeValue.getDescription();
        }
    }

    @Override
    public boolean contains(T code) {
        return map.containsKey(code);
    }

    @Override
    public Iterator<V> iterator() {
        return map.values().iterator();
    }

    @Override
    public T getFirst() {
        if (map.isEmpty()) {
            return null;
        } else {
            return map.values().iterator().next().getCode();
        }
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<V> valid() {
        List<V> list = new ArrayList<>();

        for (V decodeValue : this) {
            if (decodeValue.isValid()) {
                list.add(decodeValue);
            }
        }
        return list;
    }

    @Override
    public List<V> performSearch(T code) {
        List<V> list = new ArrayList<>();

        if (map.containsKey(code)) {
            list.add(map.get(code));
        }

        return list;
    }

    @Override
    public List<V> performSearch(String description, MapSearchType searchType, Integer sizeLimit) {
        List<V> list = new ArrayList<>();

        if (BaseFunction.isBlank(description)) {
            return list;
        }

        if (MapSearchType.flexible == searchType) {
            // Ricerca flessibile
            for (V decodeMapValue : map.values()) {
                if (decodeMapValue.getDescription() != null && decodeMapValue.getDescription().trim().equalsIgnoreCase(description.trim())) {
                    list.add(decodeMapValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }

        } else if (MapSearchType.match == searchType) {
            // Match search
            String[] matchStringArray = StringUtil.tokenize(description, " ");

            for (V decodeValue : this) {
                boolean match = true;
                for (String string : matchStringArray) {
                    if (!decodeValue.match(string)) {
                        match = false;
                        break;
                    }
                }

                if (match) {
                    list.add(decodeValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }

        } else {
            // Ricerca esatta
            for (V decodeValue : map.values()) {
                if (decodeValue.getDescription() != null && decodeValue.getDescription().equals(description)) {
                    list.add(decodeValue);
                }

                // Size Limit
                if (sizeLimit != null && list.size() >= sizeLimit) {
                    break;
                }
            }
        }

        return list;
    }

}
