package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;
import it.eg.sloth.webdesktop.controller.common.grid.ReportGridNavigationInterface;

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
 * Fornisce l'implementazione per la navigazione su una pagina di report
 *
 * @param <F>
 * @author Enrico Grillini
 */
public abstract class ReportGridPage<F extends Form> extends SimplePage<F> implements ReportGridNavigationInterface {

    public ReportGridPage() {
        super();
    }

    @Override
    public boolean defaultNavigation() throws Exception {
        if (super.defaultNavigation()) {
            return true;
        }

        String[] navigation = getWebRequest().getNavigation();

        if (navigation.length == 1) {
            if (NavigationConst.LOAD.equals(navigation[0])) {
                onLoad();
                return true;
            } else if (NavigationConst.RESET.equals(navigation[0])) {
                onReset();
                return true;
            }
        }

        if (navigation.length == 2) {
            if (NavigationConst.FIRST_ROW.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onFirstRow(grid);
                return true;
            } else if (NavigationConst.PREV_PAGE.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onPrevPage(grid);
                return true;
            } else if (NavigationConst.NEXT_PAGE.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onNextPage(grid);
                return true;
            } else if (NavigationConst.LAST_ROW.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onLastRow(grid);
                return true;
            } else if (NavigationConst.EXCEL.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onExcel(grid);
                return true;
            }
        }

        if (navigation.length == 3) {
            if (NavigationConst.SORT_ASC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortingRule.SORT_ASC_NULLS_LAST);
                return true;
            } else if (NavigationConst.SORT_DESC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortingRule.SORT_DESC_NULLS_LAST);
                return true;
            }
        }

        return false;
    }

    public void onLoad() throws Exception {
        execLoad();
    }

    public void onReset() throws Exception {
        execReset();
    }

    @Override
    public void onFirstRow(Grid<?> grid) throws Exception {
        grid.getDataSource().first();
    }

    @Override
    public void onPrevPage(Grid<?> grid) throws Exception {
        grid.getDataSource().prevPage();
    }

    @Override
    public void onNextPage(Grid<?> grid) throws Exception {
        grid.getDataSource().nextPage();
    }

    @Override
    public void onLastRow(Grid<?> grid) throws Exception {
        grid.getDataSource().last();
    }

    @Override
    public void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception {
        DataTable<?> dataTable = grid.getDataSource();

        dataTable.clearSortingRules();
        dataTable.addSortingRule(fieldName, sortType);
        dataTable.applySort(false);
    }

    @Override
    public void onExcel(Grid<?> grid) throws Exception {
        GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);

        try {
            setModelAndView(BaseFunction.nvl(grid.getDescription(), getForm().getPageInfo().getTitle()) + FileType.XLSX.getExtension(), FileType.XLSX);
            gridXlsxWriter.getWorkbook().write(getResponse().getOutputStream());
        } finally {
            getResponse().getOutputStream().close();
        }
    }

}
