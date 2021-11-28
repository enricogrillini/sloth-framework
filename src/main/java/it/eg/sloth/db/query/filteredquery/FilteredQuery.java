package it.eg.sloth.db.query.filteredquery;

import it.eg.sloth.db.query.SelectAbstractQuery;
import it.eg.sloth.db.query.SelectQueryInterface;
import it.eg.sloth.db.query.filteredquery.filter.*;
import it.eg.sloth.db.query.filteredquery.filter.DateIntervalFilter.IntervalType;
import it.eg.sloth.framework.common.base.BaseFunction;
import org.apache.commons.lang3.StringUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public class FilteredQuery extends SelectAbstractQuery implements SelectQueryInterface {

    public static final String PLACEHOLDER = "/*W*/";

    protected List<Filter> filterList;

    public FilteredQuery(String statement) {
        super(statement);
        this.filterList = new ArrayList<>();
    }

    public int addValues(PreparedStatement statement, int i) throws SQLException {
        for (Filter filtro : filterList) {
            i = filtro.addValues(statement, i);
        }

        return i;
    }

    @Override
    protected String getSqlStatement() {
        return getFilteredStatement();
    }

    @Override
    protected void initStatement(PreparedStatement statement) throws SQLException {
        int numPlaceholder = StringUtils.countMatches(getStatement(), PLACEHOLDER);
        int i = 1;
        for (int j = 0; j < numPlaceholder; j++) {
            i = addValues(statement, i);
        }
    }

    /**
     * Ritorna lo statement arricchito con i filtri
     *
     * @return
     */
    protected String getFilteredStatement() {
        StringBuilder sqlFilter = new StringBuilder();
        for (Filter filtro : filterList) {
            String statement = filtro.getWhereCondition();
            if (!BaseFunction.isBlank(statement)) {
                sqlFilter.append(sqlFilter.length() == 0 ? statement : " And " + statement);
            }
        }

        if (sqlFilter.length() != 0) {
            if (getStatement().toLowerCase().contains("where")) {
                sqlFilter.insert(0, " And ");
            } else {
                sqlFilter.insert(0, "Where ");
            }
        }

        return getStatement().replace(PLACEHOLDER, sqlFilter.toString());
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
     * Aggiunge un filtro. Esempio d'uso: addTextSearch("upper(table.column) like '%' || ? || '%'", value)
     *
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
    public void addInFilter(String sql, int sqlTypes, List<?> values) {
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
     * Aggiunge una lista di filtri REPLICANDO lo statement sql in or
     *
     * @param sql
     * @param sqlTypes
     * @param values
     */
    public void addOrFilter(String sql, int sqlTypes, Object... values) {
        filterList.add(new OrFilter(sql, sqlTypes, values));
    }


    /**
     * Aggiunge una lista di filtri REPLICANDO lo statement sql in or
     *
     * @param sql
     * @param sqlTypes
     * @param values
     */
    public void addOrFilter(String sql, int sqlTypes, List<?> values) {
        filterList.add(new OrFilter(sql, sqlTypes, values));
    }

    /**
     * Aggiunge un filtro per stabilire se la data filterDateFrom o filterDateTo
     * ricada o meno nell'intervallo [sqlStartDate,sqlEndDate] utilizzando
     * l'algoritmo del Bisi
     *
     * @param intervalType
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


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(super.toString());
        for (Filter filter : filterList) {
            stringBuilder.append("\nFilter " + filter.toString());
        }

        return stringBuilder.toString();
    }
}
