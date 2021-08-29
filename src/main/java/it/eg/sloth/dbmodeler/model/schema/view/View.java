package it.eg.sloth.dbmodeler.model.schema.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
