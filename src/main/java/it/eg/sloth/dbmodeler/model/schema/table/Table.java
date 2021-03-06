package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Table {

    private String name;
    private String description;
    private String tablespace;
    private Long initial;
    private Boolean temporary;
    private String duration;

    private List<TableColumn> columnCollection;

}
