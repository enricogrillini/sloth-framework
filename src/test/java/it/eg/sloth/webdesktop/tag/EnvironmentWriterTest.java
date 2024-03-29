package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.webdesktop.tag.pagearea.writer.EnvironmentWriter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 *
 * @author Enrico Grillini
 */
class EnvironmentWriterTest {

    private static final String CONTENT = "<div class=\"text-right p-2 pb-0\">Ambiente: &nbsp;<span class=\"badge badge-danger small\">Local</div>";

    @BeforeEach
    void init() {
        ConfigSingleton.getInstance().addProperty(ConfigSingleton.FRAMEWORK_ENVIRONMENT, "Local");
    }

    @AfterEach
    void finish() {
        ConfigSingleton.getInstance().clearProperty();
    }

    @Test
    void fieldCardContentTest() {
        assertEquals(CONTENT, EnvironmentWriter.writeEnvironment(true));
    }

}
