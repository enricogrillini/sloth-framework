package it.eg.sloth.db.model;

import it.eg.sloth.db.datasource.row.PojoRow;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class SamplePojoRow extends PojoRow {
    String testo;
    BigDecimal numero;
    Timestamp data;
    byte[] blob;
}
