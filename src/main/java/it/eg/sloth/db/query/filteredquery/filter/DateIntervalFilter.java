package it.eg.sloth.db.query.filteredquery.filter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import it.eg.sloth.framework.FrameComponent;

public class DateIntervalFilter extends FrameComponent implements Filter {

  public enum IntervalType {
    overlapped, separated
  }

  private IntervalType intervalType;
  private String sqlDateFrom;
  private String sqlDateTo;
  private Object filterDateFrom;
  private Object filterDateTo;

  public DateIntervalFilter(IntervalType intervalType, String sqlDateFrom, String sqlDateTo, Object filterDateFrom, Object filterDateTo) {
    this.intervalType = intervalType;
    this.sqlDateFrom = sqlDateFrom;
    this.sqlDateTo = sqlDateTo;
    this.filterDateFrom = filterDateFrom;
    this.filterDateTo = filterDateTo;
  }

  public IntervalType getIntervalType() {
    return intervalType;
  }

  public void setIntervalType(IntervalType intervalType) {
    this.intervalType = intervalType;
  }

  public String getSqlDateFrom() {
    return sqlDateFrom;
  }

  public void setSqlDateFrom(String sqlDateFrom) {
    this.sqlDateFrom = sqlDateFrom;
  }

  public String getSqlDateTo() {
    return sqlDateTo;
  }

  public void setSqlDateTo(String sqlDateTo) {
    this.sqlDateTo = sqlDateTo;
  }

  public Object getFilterDateFrom() {
    return filterDateFrom;
  }

  public void setFilterDateFrom(Object filterDateFrom) {
    this.filterDateFrom = filterDateFrom;
  }

  public Object getFilterDateTo() {
    return filterDateTo;
  }

  public void setFilterDateTo(Object filterDateTo) {
    this.filterDateTo = filterDateTo;
  }

  public String getWhereCondition() {
    StringBuilder inStatement = new StringBuilder("");

    if (getIntervalType().equals(IntervalType.overlapped)) {
      if (filterDateFrom != null && filterDateTo != null) {
        if (((Timestamp) filterDateFrom).getTime() > ((Timestamp) filterDateTo).getTime()) {
          inStatement.append("1 = 2");
        } else {
          inStatement.append(" (" + sqlDateFrom + " < ?  and " + sqlDateTo + " > ? ) ");
        }
      } else if (filterDateFrom != null) {

        inStatement.append(" " + sqlDateTo + " >= ? ");
      } else if (filterDateTo != null) {

        inStatement.append(" ? >=  " + sqlDateFrom);
      }
    } else {
      if (filterDateFrom != null && filterDateTo != null) {
        if (((Timestamp) filterDateFrom).getTime() > ((Timestamp) filterDateTo).getTime()) {
          inStatement.append("1 = 1");
        } else {
          inStatement.append(" Not (" + sqlDateFrom + " < ?  and " + sqlDateTo + " > ? ) ");
        }
      } else if (filterDateFrom != null) {

        inStatement.append(" " + sqlDateTo + " < ? ");
      } else if (filterDateTo != null) {

        inStatement.append(" ? <  " + sqlDateFrom);
      }
    }
    return inStatement.toString();
  }

  public int addValues(PreparedStatement statement, int i) throws SQLException {
    if (filterDateFrom != null && filterDateTo != null && ((Timestamp) filterDateFrom).getTime() > ((Timestamp) filterDateTo).getTime()) {
      return i;
    }

    if (filterDateTo != null) {
      statement.setObject(i++, filterDateTo, Types.DATE);
    }
    if (filterDateFrom != null) {
      statement.setObject(i++, filterDateFrom, Types.DATE);
    }

    return i;
  }

  public int getSqlTypes() {
    return Types.DATE;
  }

}
