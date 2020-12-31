package it.eg.sloth.form.fields.field.impl;

import it.eg.sloth.db.decodemap.DecodeMap;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.FieldType;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.SneakyThrows;
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
@SuperBuilder(toBuilder = true)
public class Semaphore extends ComboBox<String> {

    private Boolean full;

    public static final String WHITE = "W";
    public static final String GREEN = "G";
    public static final String YELLOW = "Y";
    public static final String RED = "R";
    public static final String BLACK = "B";

    static final String VALUES = "W,Bianco;G,Verde;Y,Giallo;R,Rosso;B,Nero";
    static final String SIMPLE_VALUES = "G,Verde;Y,Giallo;R,Rosso";

    static final String INNER_HTML_WHITE = "<i class=\"fas fa-circle text-secondary\"></i>";
    static final String INNER_HTML_GREEN = "<i class=\"fas fa-circle text-success\"></i>";
    static final String INNER_HTML_YELLOW = "<i class=\"fas fa-circle text-warning\"></i>";
    static final String INNER_HTML_RED = "<i class=\"fas fa-circle text-danger\"></i>";
    static final String INNER_HTML_BLACK = "<i class=\"fas fa-circle text-dark\"></i>";

    public static final StringDecodeMap SEMAPHORE_MAP = new StringDecodeMap(VALUES);
    public static final StringDecodeMap SIMPLE_SEMAPHORE_MAP = new StringDecodeMap(SIMPLE_VALUES);

    public Semaphore(String name, String description, DataTypes dataType) {
        super(name, description, dataType);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.SEMAPHORE;
    }

    public boolean isFull() {
        return full != null && full;
    }

    @SneakyThrows
    @Override
    public void setDecodeMap(DecodeMap decodeMap) {
        throw new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, "Impossibile settare la DecodeMap per un semaforo");
    }

    @Override
    public DecodeMap getDecodeMap() {
        if (isFull()) {
            return SEMAPHORE_MAP;
        } else {
            return SIMPLE_SEMAPHORE_MAP;
        }
    }


    @Override
    public String escapeHtmlDecodedText() throws FrameworkException {
        if (getValue() == null) {
            return INNER_HTML_WHITE;
        }

        switch (getValue() ) {
            case Semaphore.GREEN:
                return INNER_HTML_GREEN;

            case Semaphore.YELLOW:
                return INNER_HTML_YELLOW;

            case Semaphore.RED:
                return INNER_HTML_RED;

            case Semaphore.BLACK:
                return INNER_HTML_BLACK;

            default:
                return INNER_HTML_WHITE;
        }
    }

    @Override
    public Semaphore newInstance() {
        return toBuilder().build();
    }

}
