package it.eg.sloth.db.decodemap;

import it.eg.sloth.db.decodemap.map.BaseDecodeMap;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.framework.common.cache.CacheSingleton;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;

public abstract class DecodeMapManger {

    private static final String SQL = "Select * From {0} Order By flagValido desc, posizione";

    private static final String DECODE_PREFIX = "DECODE_MAP-";

    private DecodeMapManger() {
        // NOP
    }

    public static <T> BaseDecodeMap<T> loadFromDb(Query query, String keyName, String descriptionName, String validFlagName) throws SQLException, IOException, FrameworkException {
        BaseDecodeMap<T> result = new BaseDecodeMap<>(keyName, descriptionName, validFlagName);
        result.load(query.selectTable());

        return result;
    }

    public static <T> BaseDecodeMap<T> loadFromDb(String tableName, String keyName, String descriptionName, String validFlagName) throws SQLException, IOException, FrameworkException {
        Query query = new Query(MessageFormat.format(SQL, tableName));

        return loadFromDb(query, keyName, descriptionName, validFlagName);
    }

    public static <T> BaseDecodeMap<T> loadFromCache(String tableName, String keyName, String descriptionName, String validFlagName) throws SQLException, IOException, FrameworkException {
        String cacheKey = calcCacheKey(tableName);
        if (!CacheSingleton.getInstance().cointains(cacheKey)) {
            CacheSingleton.getInstance().put(cacheKey, loadFromDb(tableName, keyName, descriptionName, validFlagName));
        }

        return (BaseDecodeMap) CacheSingleton.getInstance().get(cacheKey);
    }

    public static void removeFromCache(String tableName) {
        String cacheKey = calcCacheKey(tableName);
        CacheSingleton.getInstance().remove(cacheKey);
    }

    public static String calcCacheKey(String tableName) {
        return (DECODE_PREFIX + tableName).toLowerCase();
    }
}
