package it.eg.sloth.db.query.filteredquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.eg.sloth.db.query.SelectAbstractQuery;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.db.query.filteredquery.filter.BaseFilter;
import it.eg.sloth.db.query.filteredquery.filter.DateIntervalFilter;
import it.eg.sloth.db.query.filteredquery.filter.DateIntervalFilter.IntervalType;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.db.query.filteredquery.filter.Filter;
import it.eg.sloth.db.query.filteredquery.filter.InFilter;
import it.eg.sloth.db.query.filteredquery.filter.MultipleFilter;
import it.eg.sloth.db.query.filteredquery.filter.NoValFilter;
import it.eg.sloth.db.query.filteredquery.filter.OnlyValFilter;
import it.eg.sloth.db.query.filteredquery.filter.SubQueryFilter;
import it.eg.sloth.db.query.filteredquery.filter.TextSearch;

/**
 * 
 * @author Enrico Grillini
 * 
 */
public class FilteredQuery extends SelectAbstractQuery implements SelectQueryInterface {

  public static final String REGEX_PLACEHOLDER = "/\\x2AW\\x2A/";

  protected List<Filter> filterList;

  public FilteredQuery(String statement) {
    super(statement);
    this.filterList = new ArrayList<Filter>();
  }

  public int addValues(PreparedStatement statement, int i) throws SQLException {
    for (Filter filtro : filterList) {
      i = filtro.addValues(statement, i);
    }

    return i;
  }

  @Override
  protected PreparedStatement getPreparedStatement(Connection connection) throws SQLException {
    PreparedStatement statement = connection.prepareStatement(getFilteredStatement());

    int numPlaceholder = StringUtil.countOccurences(getStatement(), REGEX_PLACEHOLDER);
    int i = 1;
    for (int j = 0; j < numPlaceholder; j++) {
      i = addValues(statement, i);
    }

    return statement;
  }

  /**
   * Ritorna lo statement arricchito con i filtri
   * 
   * @return
   */
  protected String getFilteredStatement() {
    String sqlFilter = "";

    for (Filter filtro : filterList) {
      String statement = filtro.getWhereCondition();
      if (!"".equals(statement)) {
        sqlFilter += sqlFilter.equals("") ? statement : " And " + statement;
      }
    }

    if (!sqlFilter.equals("")) {
      if (getStatement().toLowerCase().contains("where")) {
        sqlFilter = " And " + sqlFilter;
      } else {
        sqlFilter = "Where " + sqlFilter;
      }
    }

    return getStatement().replaceAll(REGEX_PLACEHOLDER, sqlFilter);
  }

  /**
   * Aggiunge un filtro
   * 
   * @param sql
   * @param sqlTypes
   * @param value
   */
  public void addFilter(String sql, int sqlTypes, Object value) {
    filterList.add(new BaseFilter(sql, sqlTypes, value));
  }

  /**
   * Aggiunge un filtro
   * 
   * @param sql
   */
  public void addNoValFilter(String sql) {
    filterList.add(new NoValFilter(sql));
  }

  /**
   * Aggiunge un filtro
   * 
   * @param value
   */
  public void addOnlyValFilter(int sqlTypes, Object value) {
    filterList.add(new OnlyValFilter(sqlTypes, value));
  }

  /**
   * Aggiunge un filtro
   * 
   * @param name
   * @param sql
   * @param value
   */
  public void addTextSearch(String sql, String value) {
    filterList.add(new TextSearch(sql, value));
  }

  /**
   * Aggiunge una lista di filtri (generando una clasuola strFiltro IN
   * (?,?,?..?)
   * 
   * @param sql
   * @param sqlTypes
   * @param values
   */
  public void addInFilter(String sql, int sqlTypes, Object... values) {
    filterList.add(new InFilter(sql, sqlTypes, values));
  }

  /**
   * Aggiunge una lista di filtri SENZA generare una clasuola strFiltro IN
   * (?,?,?..?)
   * 
   * @param sql
   * @param sqlTypes
   * @param values
   */
  public void addMultipleFilter(String sql, int sqlTypes, Object... values) {
    filterList.add(new MultipleFilter(sql, sqlTypes, values));
  }

  /**
   * Aggiunge un filtro per stabilire se la data filterDateFrom o filterDateTo
   * ricada o meno nell'intervallo [sqlStartDate,sqlEndDate] utilizzando
   * l'algoritmo del Bisi à
   * 
   * @param sqlTypes
   * @param sqlStartDate
   * @param sqlEndDate
   * @param filterDateFrom
   * @param filterDateTo
   */
  public void addDateIntervalFilter(IntervalType intervalType, String sqlStartDate, String sqlEndDate, Object filterDateFrom, Object filterDateTo) {
    filterList.add(new DateIntervalFilter(intervalType, sqlStartDate, sqlEndDate, filterDateFrom, filterDateTo));
  }

  /**
   * Aggiunge una SubQuery
   * 
   * @param subQuery
   */
  public void addSubQueryFilter(FilteredQuery subQuery) {
    filterList.add(new SubQueryFilter(subQuery));
  }

}
