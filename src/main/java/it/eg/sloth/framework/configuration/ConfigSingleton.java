package it.eg.sloth.framework.configuration;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.utility.files.FileSystemUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

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
 *
 * Singleton per la gestione della configurazione applicativa
 *
 * @author Enrico Grillini
 */
@Slf4j
public class ConfigSingleton extends FrameComponent {

    public static final String APPLICATION_PROPERTIES = "application.properties";

    public static final String FRAMEWORK_DOCUMENTATION_URL = "sloth.documentation.url";

    @Getter
    private Properties properties;

    private static ConfigSingleton instance = null;

    private ConfigSingleton() {
        log.info("ConfigSingleton STARTING");

        try {
            File propertiesFile = FileSystemUtil.getFileFromContext(APPLICATION_PROPERTIES);

            properties = new Properties();
            try (FileInputStream inputStream = new FileInputStream(propertiesFile)) {
                properties.load(inputStream);
            }
        } catch (Exception e) {
            log.error("Impossibile leggere il file di configurazione: {} ", APPLICATION_PROPERTIES, e);
        }

        log.info("ConfigSingleton STARTED");
    }

    public static synchronized ConfigSingleton getInstance() {
        if (instance == null) {
            instance = new ConfigSingleton();
        }

        return instance;
    }

    public String getProperty(String propertyKey) {
        return properties.getProperty(propertyKey);
    }

}
