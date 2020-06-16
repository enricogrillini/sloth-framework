package it.eg.sloth.framework.common.cache;

import java.util.HashMap;
import java.util.Map;

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
public final class CacheSingleton {

    private static CacheSingleton instance;

    private Map<String, Object> map;

    private CacheSingleton() {
        init();
    }

    private void init() {
        map = new HashMap<>();
    }

    public static synchronized CacheSingleton getInstance() {
        if (instance == null) {
            instance = new CacheSingleton();
        }

        return instance;
    }

    public Object get(String name) {
        synchronized (CacheSingleton.class) {
            return map.get(name);
        }
    }

    public void put(String name, Object object) {
        synchronized (CacheSingleton.class) {
            map.put(name, object);
        }
    }

    public void clear() {
        synchronized (CacheSingleton.class) {
            init();
        }
    }

    public void clear(String key) {
        synchronized (CacheSingleton.class) {
            map.remove(key);
        }
    }

}
