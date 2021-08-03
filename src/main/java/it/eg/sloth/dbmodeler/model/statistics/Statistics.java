package it.eg.sloth.dbmodeler.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Statistics {

    String driverClass;
    long tableCount;
    long tableSize;
    long indexCount;
    long indexSize;
    long recycleBinCount;
    long recycleBinSize;

}
