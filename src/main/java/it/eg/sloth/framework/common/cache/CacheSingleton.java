package it.eg.sloth.framework.common.cache;

import java.util.HashMap;
import java.util.Map;

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
