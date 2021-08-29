package it.eg.sloth.dbmodeler.model.schema.view;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewColumn {

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
