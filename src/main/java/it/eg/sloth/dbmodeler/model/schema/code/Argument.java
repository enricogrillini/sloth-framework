package it.eg.sloth.dbmodeler.model.schema.code;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Argument {

    private String name;
    private String type;
    private ArgumentType inOut;
    private Integer position;
}
