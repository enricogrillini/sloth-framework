package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import lombok.experimental.SuperBuilder;

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
 * <p>
 *
 * @author Enrico Grillini
 */
@SuperBuilder
public class Semaphore extends ComboBox<String> {

    public static final String WHITE = "W";
    public static final String GREEN = "G";
    public static final String YELLOW = "Y";
    public static final String RED = "R";
    public static final String BLACK = "B";

    static final String VALUES = "W,Bianco;G,Verde;Y,Giallo;R,Rosso;B,Nero";

    static final String GIF_WHITE = "<i class=\"fas fa-circle text-secondary\"></i>";
    static final String GIF_GREEN = "<i class=\"fas fa-circle text-success\"></i>";
    static final String GIF_YELLOW = "<i class=\"fas fa-circle text-warning\"></i>";
    static final String GIF_RED = "<i class=\"fas fa-circle text-danger\"></i>";
    static final String GIF_BLACK = "<i class=\"fas fa-circle text-dark\"></i>";

    public static final StringDecodeMap SEMAFORO_MAP = new StringDecodeMap(VALUES);

    public Semaphore(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
        setDecodeMap(SEMAFORO_MAP);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.SEMAPHORE;
    }

    @Override
    public String escapeHtmlDecodedText() {
        switch (getValue()) {
            case Semaphore.WHITE:
                return GIF_WHITE;

            case Semaphore.GREEN:
                return GIF_GREEN;

            case Semaphore.YELLOW:
                return GIF_YELLOW;

            case Semaphore.RED:
                return GIF_RED;

            case Semaphore.BLACK:
                return GIF_BLACK;

            default:
                return GIF_WHITE;
        }

    }

}
