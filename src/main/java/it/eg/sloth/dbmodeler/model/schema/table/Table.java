package it.eg.sloth.dbmodeler.model.schema.table;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Table {

    String name;
    String description;
    String tablespace;
    Double initial;
    Boolean temporary;
    String duration;

}
