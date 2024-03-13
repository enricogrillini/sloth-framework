package it.eg.sloth.webdesktop.api.request;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Locale;
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
public class BffFields {

    private Map<String, String> names;

    public BffFields() {
        names = new LinkedHashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                names.put(field.getName().toLowerCase(), field.getName());
            }
        }
    }

    public String getString(String name) throws FrameworkException {
        try {
            name = name.toLowerCase();
            if (names.containsKey(name)) {
                String methodName = "get" + names.get(name).substring(0, 1).toUpperCase(Locale.ROOT) + names.get(name).substring(1);
                Method method = this.getClass().getMethod(methodName);
                return (String) method.invoke(this);
            } else {
                return null;
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new FrameworkException(ExceptionCode.INVALID_GET, name);
        }
    }

    public void setString(String name, String value) throws FrameworkException {
        try {
            name = name.toLowerCase();
            if (names.containsKey(name)) {
                String methodName = "set" + names.get(name).substring(0, 1).toUpperCase(Locale.ROOT) + names.get(name).substring(1);
                Method method = this.getClass().getMethod(methodName, String.class);
                method.invoke(this, value);
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new FrameworkException(ExceptionCode.INVALID_SET, name);
        }
    }



}
