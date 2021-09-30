package it.eg.sloth.webdesktop.tag.pagearea.writer;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.configuration.ConfigSingleton;

import java.text.MessageFormat;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public class EnvironmentWriter {

    private static final String HTML_ENV = "<div class=\"text-right p-2 pb-0\">{0}<span class=\"badge badge-danger small\">{1}</div>";

    private EnvironmentWriter() {
        // NOP
    }

    public static String writeEnvironment(boolean writeLabel) {
        String label = writeLabel ? "Ambiente: &nbsp;" : "";
        String environment = ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_ENVIRONMENT);

        if (BaseFunction.isBlank(environment)) {
            return StringUtil.EMPTY;
        } else {
            return MessageFormat.format(HTML_ENV, label, environment);
        }
    }

}
