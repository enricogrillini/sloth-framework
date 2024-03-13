package it.eg.sloth.db.decodemap.map;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.DecodeValue;
import it.eg.sloth.db.decodemap.SearchType;
import it.eg.sloth.framework.common.base.BaseFunction;
import lombok.ToString;

import java.util.*;

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
@ToString
public abstract class AbstractDecodeMap<T, V extends DecodeValue<T>> implements DecodeMap<T, V> {

    private Map<T, V> map;

    protected AbstractDecodeMap() {
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
        Collection<V> list = performSearch(description, SearchType.IGNORE_CASE, 2);

        Optional<V> optional = list.stream().findFirst();
        if (optional.isPresent()) {
            return optional.get().getCode();
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
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Collection<V> valid() {
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
    public Collection<V> performSearch(String query, SearchType searchType, Integer sizeLimit) {
        if (BaseFunction.isBlank(query)) {
            return new ArrayList<>();
        }

        Set<V> set = new LinkedHashSet<>();
        set.addAll(executeSearch(query, SearchType.EXACT, true,sizeLimit - set.size(), set));
        if (! set.isEmpty()) {
            return set;
        }

        if (searchType == SearchType.MATCH) {
            set.addAll(executeSearch(query, SearchType.LIKE_START, true,sizeLimit - set.size(), set));
            set.addAll(executeSearch(query, SearchType.LIKE_CONTAINS, true,sizeLimit - set.size(), set));
            set.addAll(executeSearch(query, SearchType.PATTERN_MATCH, true,sizeLimit - set.size(), set));
            set.addAll(executeSearch(query, SearchType.LIKE_START, false,sizeLimit - set.size(), set));
            set.addAll(executeSearch(query, SearchType.LIKE_CONTAINS, false,sizeLimit - set.size(), set));
            set.addAll(executeSearch(query, SearchType.PATTERN_MATCH, false,sizeLimit - set.size(), set));
        }

        set.addAll(executeSearch(query, searchType, true,sizeLimit - set.size(), set));
        set.addAll(executeSearch(query, searchType, false,sizeLimit - set.size(), set));

        return set;
    }

    private Collection<V> executeSearch(String query, SearchType searchType, boolean valid, Integer sizeLimit, Set<V> excludeValue) {
        List<V> list = new ArrayList<>();
        if (sizeLimit == null || sizeLimit == 0) {
            return list;
        }

        for (V decodeMapValue : map.values()) {
            if (valid != decodeMapValue.isValid()) {
                continue;
            }

            // Size Limit
            if (list.size() >= sizeLimit) {
                return list;
            }

            if (!excludeValue.contains(decodeMapValue) && searchType.match(decodeMapValue.getDescription(), query)) {
                list.add(decodeMapValue);
            }
        }

        return list;
    }

}
