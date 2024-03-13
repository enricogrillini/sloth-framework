package it.eg.sloth.dbmodeler.model.schema.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Column {

    private String name;
    private String description;
    private String type;
    private Integer position;
    private Integer dataLength;
    private Integer dataPrecision;

    @JsonIgnore
    public boolean isPlain() {
        return !isLob();
    }

    @JsonIgnore
    public boolean isByteA() {
        return "bytea".equalsIgnoreCase(getType());
    }

    @JsonIgnore
    public boolean isLob() {
        return isBlob() || isClob();
    }

    @JsonIgnore
    public boolean isBlob() {
        return "BLOB".equalsIgnoreCase(getType());
    }

    @JsonIgnore
    public boolean isClob() {
        return "CLOB".equalsIgnoreCase(getType());
    }

}
