package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class Constraint {

    private String name;
    private ConstraintType type;
    private String searchCondition;
    private boolean generated;
    private String referenceTable;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<String> columns;

    @Builder
    public Constraint(String name, ConstraintType type, String searchCondition, boolean generated, String referenceTable) {
        this.name = name;
        this.type = type;
        this.searchCondition = searchCondition;
        this.generated = generated;
        this.referenceTable = referenceTable;

        columns = new ArrayList<>();
    }

    public Constraint() {
        this(null, null, null, false, null);
    }

    // Columns
    public Collection<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public void addColumn(String columnName) {
        columns.add(columnName);
    }

}
