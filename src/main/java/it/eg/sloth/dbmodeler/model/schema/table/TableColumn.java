package it.eg.sloth.dbmodeler.model.schema.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableColumn {

    private String name;
    private String description;
    private boolean nullable;
    private String type;
    private Integer position;
    private Integer dataLength;
    private Integer dataPrecision;

    private boolean primaryKey;
    private String defaultValue;

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
