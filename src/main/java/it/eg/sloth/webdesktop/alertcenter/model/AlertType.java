package it.eg.sloth.webdesktop.alertcenter.model;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;

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
public enum AlertType {
    INFO("<i class=\"fas fa-info-circle text-white\"></i>"), WARNING("<i class=\"fas fa-exclamation-triangle text-white\"></i>"), DANGER("<i class=\"fas fa-times-circle text-white\"></i>");

    private String icon;


    public String getIcon() {
        return icon;
    }

    AlertType(String icon) {
        this.icon = icon;
    }

    public static final StringDecodeMap DECODE_MAP = new StringDecodeMap("INFO,Info;WARNING,Warning;DANGER,Danger");

}
