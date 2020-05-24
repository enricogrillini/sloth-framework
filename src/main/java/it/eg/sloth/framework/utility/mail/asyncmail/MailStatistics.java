package it.eg.sloth.framework.utility.mail.asyncmail;

import java.math.BigDecimal;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.row.Row;

public class MailStatistics {
  private int success;
  private int fail;

  public MailStatistics(int success, int fail) {
    this.success = success;
    this.fail = fail;
  }

  public int getSuccess() {
    return success;
  }

  public void setSuccess(int success) {
    this.success = success;
  }

  public int getFail() {
    return fail;
  }

  public void setFail(int fail) {
    this.fail = fail;
  }
  
  public DataRow getRow() {
    Row row = new Row();
    row.setBigDecimal("success", new BigDecimal(getSuccess()));
    row.setBigDecimal("fail", new BigDecimal(getFail()));
    
    return row;
  }

}
