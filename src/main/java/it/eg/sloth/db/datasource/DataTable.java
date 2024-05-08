package it.eg.sloth.db.datasource;

import it.eg.sloth.db.datasource.table.filter.FilterRules;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.db.datasource.table.sort.SortingRules;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.BigDecimalUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public interface DataTable<T extends DataRow> extends DataSource, DataRow, Iterable<T> {

    /**
     * Restituisce il numero di righe di cui à composto l'elenco
     *
     * @return
     */
    int size();

    /**
     * Restituisce il numero di pagine di cui à composto l'elenco
     *
     * @return
     */
    int pages();

    /**
     * Va al primo record
     */
    void first();

    /**
     * Retrocede di una pagina
     */
    void prevPage();

    /**
     * Retrocede di un record
     */
    void prev();

    /**
     * Imposta la riga corrente
     *
     * @param currentRow
     */
    void setCurrentRow(int currentRow);

    /**
     * Ritorna la pagina corrente dell'elenco
     *
     * @return
     */
    int getCurrentPage();

    /**
     * Avanza di un record
     */
    void next();

    /**
     * Avanza di una pagina
     */
    void nextPage();

    /**
     * Va all'ultimo record
     */
    void last();

    /**
     * Ritorna la riga corrente
     *
     * @return
     */
    T getRow();

    /**
     * Ritorna le righe
     *
     * @return
     */
    List<T> getRows();

    /**
     * Restituisce un iterator sulle righe
     *
     * @return
     */
    Iterator<T> iterator();

    /**
     * Restituisce un iterator sulle righe della pagina
     *
     * @return
     */
    Iterator<T> pageIterator();

    /**
     * Restituisce l'indice della riga corrente
     *
     * @return
     */
    int getCurrentRow();

    /**
     * Imposta la nuova pagina corrente
     *
     * @param currentPage
     */
    void setCurrentPage(int currentPage);

    /**
     * Ritorna la dimensione della pagina
     *
     * @return
     */
    int getPageSize();

    /**
     * Imposta la pagina corrente
     *
     * @param pageSize
     */
    void setPageSize(int pageSize);

    /**
     * Ritorna la prima riga della pagina corrente
     *
     * @return
     */
    int getPageStart();

    /**
     * Ritorna l'ultima riga della pagina corrente
     *
     * @return
     */
    int getPageEnd();

    /**
     * Ritorna true se il record corrente à sulla prima pagina
     *
     * @return
     */
    boolean isFirstPage();

    /**
     * Ritorna true se il record corrente à sull'ultima pagina
     *
     * @return
     */
    boolean isLastPage();

    /**
     * Ritorna true se il record corrente à il primo
     *
     * @return
     */
    boolean isFirst();

    /**
     * Ritorna true se il record corrente à l'ultimo
     *
     * @return
     */
    boolean isLast();

    /**
     * Aggiunge una riga in coda
     */
    T append();

    /**
     * Aggiunge una riga dopo la riga corrente
     */
    T add();

    /**
     * Aggiunge una riga dopo la riga corrente
     */
    T add(T row);

    /**
     * Rimuove la riga corrente
     *
     * @return
     * @throws FrameworkException
     */
    T remove() throws FrameworkException;

    void removeByFilter(FilterRules filterRules) throws FrameworkException;

    /**
     * Rimuove tutte le righe
     *
     * @throws FrameworkException
     */
    void removeAllRow() throws FrameworkException;

    /**
     * Aggiunge una regola di ordinamento
     *
     * @param fieldName
     */
    SortingRule addSortingRule(String fieldName);

    /**
     * Aggiunge una regola di ordinamento
     *
     * @param fieldName
     * @param sortType
     * @return
     */
    SortingRule addSortingRule(String fieldName, SortType sortType);

    /**
     * Applica l'ordinamento memorizzato
     */
    void applySort();

    /**
     * Applica l'ordinamento passato
     */
    void applySort(String fieldName, SortType sortType);

    /**
     * Applica l'ordinamento passato
     */
    void applySorts(SortingRules<T> sortingRules);

    /**
     * Effettua l'ordinamento gestendo o meno il mantenimento della riga corrente
     *
     * @param preserveCurrentRow
     */
    void applySort(boolean preserveCurrentRow);

    /**
     * Ritorna i sort
     *
     * @return
     */
    SortingRules<T> getSortingRules();

    /**
     * Rimuove l'ordinamento della tabella
     *
     * @return
     */
    void clearSortingRules();

    /**
     * Ritorna il set con i valori della colonna specificata applicando i filtri
     * passati
     *
     * @param filterNames
     * @param filterValues
     * @param columnName
     * @return
     */
    default Set<Object> distinct(String[] filterNames, Object[] filterValues, String columnName) {
        Set<Object> result = new LinkedHashSet<>();

        for (DataRow row : this) {
            boolean filter = true;
            if (filterNames != null) {
                for (int i = 0; i < filterNames.length; i++) {
                    if (!BaseFunction.equals(row.getObject(filterNames[i]), filterValues[i])) {
                        filter = false;
                        break;
                    }
                }
            }

            if (filter && !result.contains(row.getObject(columnName))) {
                result.add(row.getObject(columnName));
            }
        }

        return result;
    }

    /**
     * Ritorna il set con i valori della colonna specificata applicando i filtro
     * passato
     *
     * @param filterName
     * @param filterValue
     * @param columnName
     * @return
     */
    default Set<Object> distinct(String filterName, Object filterValue, String columnName) {
        return distinct(new String[]{filterName}, new Object[]{filterValue}, columnName);
    }

    /**
     * Ritorna il set con i valori della colonna specificata
     *
     * @param columnName
     * @return
     */
    default Set<Object> distinct(String columnName) {
        return distinct((String[]) null, null, columnName);
    }

    // Effettua la somma sulla dataTable passata applicando i filtri indicati
    default BigDecimal sum(String[] filterNames, Object[] filterValues, String columnName) {
        BigDecimal result = null;

        for (DataRow row : this) {
            if (considerRow(row, filterNames, filterValues)) {
                result = BigDecimalUtil.sum(result, row.getBigDecimal(columnName));
            }
        }

        return result;
    }

    // Effettua la somma sulla dataTable passata applicando il filtro indicato
    default BigDecimal sum(String filterName, Object filterValue, String columnName) {
        return sum(new String[]{filterName}, new Object[]{filterValue}, columnName);
    }

    // Effettua la somma sulla dataTable passata
    default BigDecimal sum(String columnName) {
        return sum((String[]) null, null, columnName);
    }

    // Cerca il minimo sulla dataTable passata applicando i filtri indicati
    default BigDecimal min(String[] filterNames, Object[] filterValues, String columnName) {
        BigDecimal result = null;
        for (DataRow row : this) {
            if (considerRow(row, filterNames, filterValues)) {
                result = BigDecimalUtil.min(result, row.getBigDecimal(columnName));
            }
        }

        return result;
    }

    // Cerca il minimo sulla dataTable passata applicando il filtro indicato
    default BigDecimal min(String filterName, Object filterValue, String columnName) {
        return min(new String[]{filterName}, new Object[]{filterValue}, columnName);
    }

    // Cerca il minimo sulla dataTable passata
    default BigDecimal min(String columnName) {
        return min((String[]) null, null, columnName);
    }


    // Cerca il minimo sulla dataTable passata applicando i filtri indicati
    default BigDecimal max(String[] filterNames, Object[] filterValues, String columnName) {
        BigDecimal result = null;
        for (DataRow row : this) {
            if (considerRow(row, filterNames, filterValues)) {
                result = BigDecimalUtil.max(result, row.getBigDecimal(columnName));
            }
        }

        return result;
    }

    // Cerca il minimo sulla dataTable passata applicando il filtro indicato
    default BigDecimal max(String filterName, Object filterValue, String columnName) {
        return min(new String[]{filterName}, new Object[]{filterValue}, columnName);
    }

    // Cerca il minimo sulla dataTable passata
    default BigDecimal max(String columnName) {
        return min((String[]) null, null, columnName);
    }

    private boolean considerRow(DataRow row, String[] filterNames, Object[] filterValues) {
        if (filterNames != null) {
            for (int i = 0; i < filterNames.length; i++) {
                if (!BaseFunction.equals(row.getObject(filterNames[i]), filterValues[i])) {
                    return false;
                }
            }
        }

        return true;
    }

}
