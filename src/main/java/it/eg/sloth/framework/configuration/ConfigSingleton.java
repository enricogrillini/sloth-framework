package it.eg.sloth.framework.configuration;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
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
 * Singleton per la gestione della configurazione applicativa
 *
 * @author Enrico Grillini
 */
@Slf4j
public class ConfigSingleton {
    public static final String FRAMEWORK_APP_TITLE = "sloth.app.title";
    public static final String FRAMEWORK_LOGO_LEFT = "sloth.logo.left";
    public static final String FRAMEWORK_LOGO_RIGHT = "sloth.logo.right";
    public static final String FRAMEWORK_LOGO_URL = "sloth.logo.url";

    public static final String FRAMEWORK_DOCUMENTATION_URL = "sloth.documentation.url";
    public static final String FRAMEWORK_ENVIRONMENT = "sloth.environment";

    private Map<String, Object> properties;

    private static ConfigSingleton instance = null;

    private ConfigSingleton() {
        log.info("ConfigSingleton STARTING");

        properties = new HashMap<>();

        log.info("ConfigSingleton STARTED");
    }

    public static synchronized ConfigSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigSingleton();
        }

        return instance;
    }

    public synchronized void clearProperty() {
        properties.clear();
    }

    public synchronized void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String propertyKey) {
        return properties.get(propertyKey);
    }

    public String getString(String propertyKey) {
        Object object = properties.get(propertyKey);
        if (!BaseFunction.isNull(object)) {
            return object.toString();
        } else {
            return StringUtil.EMPTY;
        }
    }

}
