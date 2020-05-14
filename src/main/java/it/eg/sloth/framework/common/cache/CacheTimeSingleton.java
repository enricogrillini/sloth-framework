package it.eg.sloth.framework.common.cache;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import it.eg.sloth.framework.common.base.TimeStampUtil;

public final class CacheTimeSingleton {

  public static final int DURATION_MINUTE = 60;
  public static final int DURATION_HOUR = DURATION_MINUTE * 60;
  public static final int DURATION_DAY = DURATION_HOUR * 24;

  private static CacheTimeSingleton instance;

  private Map<String, CacheEntry> map;

  private CacheTimeSingleton() {
    init();
  }

  private void init() {
    map = new HashMap<>();
  }

  private void validateCache() {
    for (String key : new HashSet<String>(map.keySet())) {
      if (!map.get(key).isValid()) {
        map.remove(key);
      }
    }
  }

  public static CacheTimeSingleton getInstance() {
    if (instance == null) {
      synchronized (CacheTimeSingleton.class) {
        if (instance == null) {
          instance = new CacheTimeSingleton();
        }
      }
    }

    return instance;
  }

  public Object get(String name) {
    synchronized (CacheTimeSingleton.class) {
      validateCache();

      if (map.containsKey(name)) {
        return map.get(name).getValue();
      } else {
        return null;
      }
    }
  }

  /**
   * Inserisce una entry nella cache con validità fino alla data specificata
   * 
   * @param name
   * @param value
   * @param duration
   */
  public void put(String name, Object value, Timestamp exptirationDate) {
    synchronized (CacheTimeSingleton.class) {
      validateCache();
      map.put(name, new CacheEntry(value, exptirationDate));
    }
  }

  /**
   * Inserisce una entry nella cache con validità in secondi
   * 
   * @param name
   * @param value
   * @param duration
   */
  public void put(String name, Object value, int duration) {
    synchronized (CacheTimeSingleton.class) {
      validateCache();
      map.put(name, new CacheEntry(value, duration));
    }
  }

  public void clear() {
    synchronized (CacheSingleton.class) {
      init();
    }
  }

  private static class CacheEntry {

    private Object value;
    private Timestamp expirationDate;

    public CacheEntry(Object value, Timestamp expirationDate) {
      this.expirationDate = expirationDate;
      this.value = value;
    }

    public CacheEntry(Object value, int duration) {
      this.expirationDate = new Timestamp(TimeStampUtil.sysdate().getTime() + duration * 1000);
      this.value = value;
    }

    public Object getValue() {
      return value;
    }

    public boolean isValid() {
      return TimeStampUtil.sysdate().before(expirationDate);
    }
  }

}
