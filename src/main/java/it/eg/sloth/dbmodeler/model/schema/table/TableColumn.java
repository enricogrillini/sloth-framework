package it.eg.sloth.dbmodeler.model.schema.table;

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
    private Boolean nullable;
    private String type;
    private Integer position;
    private Integer dataLength;
    private Integer dataPrecision;

    private Boolean primaryKey;
    private String defaultValue;

}
