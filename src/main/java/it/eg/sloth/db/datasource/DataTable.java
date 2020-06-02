package it.eg.sloth.db.datasource;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.db.datasource.table.sort.SortingRules;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
    public void first();

    /**
     * Retrocede di una pagina
     */
    public void prevPage();

    /**
     * Retrocede di un record
     */
    public void prev();

    /**
     * Imposta la riga corrente
     *
     * @param currentRow
     */
    public void setCurrentRow(int currentRow);

    /**
     * Ritorna la pagina corrente dell'elenco
     *
     * @return
     */
    public int getCurrentPage();

    /**
     * Avanza di un record
     */
    public void next();

    /**
     * Avanza di una pagina
     */
    public void nextPage();

    /**
     * Va all'ultimo record
     */
    public void last();

    /**
     * Ritorna la riga corrente
     *
     * @return
     */
    public T getRow();

    /**
     * Ritorna le righe
     *
     * @return
     */
    public List<T> getRows();

    /**
     * Restituisce un iterator sulle righe
     *
     * @return
     */
    public Iterator<T> iterator();

    /**
     * Restituisce un iterator sulle righe della pagina
     *
     * @return
     */
    public Iterator<T> pageIterator();

    /**
     * Restituisce l'indice della riga corrente
     *
     * @return
     */
    public int getCurrentRow();

    /**
     * Imposta la nuova pagina corrente
     *
     * @param currentPage
     */
    public void setCurrentPage(int currentPage);

    /**
     * Ritorna la dimensione della pagina
     *
     * @return
     */
    public int getPageSize();

    /**
     * Imposta la pagina corrente
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize);

    /**
     * Ritorna la prima riga della pagina corrente
     *
     * @return
     */
    public int getPageStart();

    /**
     * Ritorna l'ultima riga della pagina corrente
     *
     * @return
     */
    public int getPageEnd();

    /**
     * Ritorna true se il record corrente à sulla prima pagina
     *
     * @return
     */
    public boolean isFirstPage();

    /**
     * Ritorna true se il record corrente à sull'ultima pagina
     *
     * @return
     */
    public boolean isLastPage();

    /**
     * Ritorna true se il record corrente à il primo
     *
     * @return
     */
    public boolean isFirst();

    /**
     * Ritorna true se il record corrente à l'ultimo
     *
     * @return
     */
    public boolean isLast();

    /**
     * Aggiunge una riga in coda
     */
    public T append();

    /**
     * Aggiunge una riga dopo la riga corrente
     */
    public T add();

    /**
     * Rimuove la riga corrente
     *
     * @return
     * @throws FrameworkException
     */
    public T remove() throws FrameworkException;

    /**
     * Rimuove tutte le righe
     *
     * @throws FrameworkException
     */
    public void removeAllRow() throws FrameworkException;

    /**
     * Aggiunge una regola di ordinamento
     *
     * @param fieldName
     */
    public SortingRule addSortingRule(String fieldName);

    /**
     * Aggiunge una regola di ordinamento
     *
     * @param fieldName
     * @param sortType
     * @return
     */
    public SortingRule addSortingRule(String fieldName, int sortType);

    /**
     * Applica l'ordinamento memorizzato
     */
    public void applySort();

    /**
     * Applica l'ordinamento passato
     */
    public void applySort(String fieldName, int sortType);

    /**
     * Applica l'ordinamento passato
     */
    public void applySorts(SortingRules<T> sortingRules);

    /**
     * Effettua l'ordinamento gestendo o meno il mantenimento della riga corrente
     *
     * @param preserveCurrentRow
     */
    public void applySort(boolean preserveCurrentRow);

    /**
     * Ritorna i sort
     *
     * @return
     */
    public SortingRules<T> getSortingRules();

    /**
     * Rimuove l'ordinamento della tabella
     *
     * @return
     */
    public void clearSortingRules();

    public static class Util {
        /**
         * Ritorna il set con i valori della colonna specificata applicando i filtri
         * passati
         *
         * @param dataTable
         * @param filterNames
         * @param filterValues
         * @param columnName
         * @return
         */
        public static final Set<Object> distinct(DataTable<?> dataTable, String[] filterNames, Object[] filterValues, String columnName) {
            Set<Object> result = new LinkedHashSet<>();

            for (DataRow row : dataTable) {
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
         * @param dataTable
         * @param filterName
         * @param filterValue
         * @param columnName
         * @return
         */
        public static final Set<Object> distinct(DataTable<?> dataTable, String filterName, Object filterValue, String columnName) {
            return distinct(dataTable, new String[]{filterName}, new Object[]{filterValue}, columnName);
        }

        /**
         * Ritorna il set con i valori della colonna specificata
         *
         * @param dataTable
         * @param columnName
         * @return
         */
        public static final Set<Object> distinct(DataTable<?> dataTable, String columnName) {
            return distinct(dataTable, (String[]) null, (Object[]) null, columnName);
        }

        /**
         * Effettua la somma sulla dataTable passata applicando i filtri indicati
         *
         * @param dataTable
         * @param filterNames
         * @param filterValues
         * @param columnName
         * @return
         */
        public static final BigDecimal sum(DataTable<?> dataTable, String[] filterNames, Object[] filterValues, String columnName) {
            BigDecimal result = null;

            for (DataRow row : dataTable) {
                boolean filter = true;
                if (filterNames != null) {
                    for (int i = 0; i < filterNames.length; i++) {
                        if (!BaseFunction.equals(row.getObject(filterNames[i]), filterValues[i])) {
                            filter = false;
                            break;
                        }
                    }
                }

                if (filter) {
                    if (result == null) {
                        result = row.getBigDecimal(columnName);
                    } else {
                        result = result.add(row.getBigDecimal(columnName));
                    }
                }
            }

            return result;
        }

        /**
         * Effettua la somma sulla dataTable passata applicando il filtro indicato
         *
         * @param dataTable
         * @param filterName
         * @param filterValue
         * @param columnName
         * @return
         */
        public static final BigDecimal sum(DataTable<?> dataTable, String filterName, Object filterValue, String columnName) {
            return sum(dataTable, new String[]{filterName}, new Object[]{filterValue}, columnName);
        }

        /**
         * Effettua la somma sulla dataTable passata
         *
         * @param dataTable
         * @param columnName
         * @return
         */
        public static final BigDecimal sum(DataTable<?> dataTable, String columnName) {
            return sum(dataTable, (String[]) null, (Object[]) null, columnName);
        }
    }

}
