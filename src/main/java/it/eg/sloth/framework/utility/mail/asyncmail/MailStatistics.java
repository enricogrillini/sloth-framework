package it.eg.sloth.framework.utility.mail.asyncmail;

import java.math.BigDecimal;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.row.Row;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailStatistics {
    private int success;
    private int fail;

    public DataRow getRow() {
        Row row = new Row();
        row.setBigDecimal("success", new BigDecimal(getSuccess()));
        row.setBigDecimal("fail", new BigDecimal(getFail()));

        return row;
    }
}
