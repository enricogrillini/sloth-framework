package it.eg.sloth.db.datasource.row;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
public abstract class PojoRow implements DataRow {

    private static final String LOG_MESSAGE = "Errore sul campo {}";

    Map<String, String> names;
    Map<String, Class<?>> types;

    public PojoRow() {
        names = new LinkedHashMap<>();
        types = new LinkedHashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class ||
                    field.getType() == BigDecimal.class ||
                    field.getType() == Timestamp.class ||
                    field.getType() == byte[].class) {

                names.put(field.getName().toLowerCase(), field.getName());
                types.put(field.getName().toLowerCase(), field.getType());
            }
        }
    }

    @Override
    public Object getObject(String name) {
        try {
            name = name.toLowerCase();
            if (names.containsKey(name)) {
                Method method = this.getClass().getMethod("get" + StringUtil.initCap(names.get(name)));
                return method.invoke(this);
            } else {
                return null;
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error(LOG_MESSAGE, name, e);
            return null;
        }
    }

    @Override
    public void setObject(String name, Object value) {
        try {
            name = name.toLowerCase();
            if (names.containsKey(name)) {
                Method method = this.getClass().getMethod("set" + StringUtil.initCap(names.get(name)), types.get(name));
                method.invoke(this, value);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error(LOG_MESSAGE, name, e);
        }
    }

    @Override
    public Collection<String> keys() {
        return names.keySet();
    }

    @Override
    public Collection<Object> values() {
        List<Object> list = new ArrayList<>();
        for (String name : names.keySet()) {
            list.add(getObject(name));
        }

        return list;
    }

    @Override
    public void clear() {
        for (String name : names.keySet()) {
            setObject(name, null);
        }
    }

}
