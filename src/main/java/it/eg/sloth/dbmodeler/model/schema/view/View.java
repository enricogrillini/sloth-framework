package it.eg.sloth.dbmodeler.model.schema.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
public class View {

    private String name;
    private String description;
    private String definition;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, ViewColumn> viewColumnMap;

    public View() {
        this(null, null, null);
    }

    @Builder
    public View(String name, String description, String definition) {
        this.name = name;
        this.description = description;
        this.definition = definition;

        viewColumnMap = new LinkedHashMap<>();
    }

    // ViewColumn
    public Collection<ViewColumn> getViewColumnCollection() {
        return viewColumnMap.values();
    }

    @JsonIgnore
    public Collection<ViewColumn> getPlainColumnCollection() {
        return getViewColumnCollection().stream()
                .filter(ViewColumn::isPlain)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<ViewColumn> getClobColumnCollection() {
        return getViewColumnCollection().stream()
                .filter(ViewColumn::isClob)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public Collection<ViewColumn> getBlobColumnCollection() {
        return getViewColumnCollection().stream()
                .filter(ViewColumn::isBlob)
                .collect(Collectors.toList());
    }


    @JsonIgnore
    public Collection<ViewColumn> getByteaColumnCollection() {
        return getViewColumnCollection().stream()
                .filter(ViewColumn::isByteA)
                .collect(Collectors.toList());
    }

    public void setViewColumnCollection(Collection<ViewColumn> tableColumnCollection) {
        tableColumnCollection.forEach(this::addViewColumn);
    }

    public View addViewColumn(ViewColumn tableColumn) {
        viewColumnMap.put(tableColumn.getName().toLowerCase(), tableColumn);
        return this;
    }

    public ViewColumn getViewColumn(String tableColumnName) {
        return viewColumnMap.get(tableColumnName.toLowerCase());
    }

    public ViewColumn getViewColumn(int index) {
        return (ViewColumn) viewColumnMap.values().toArray()[index];
    }

}
