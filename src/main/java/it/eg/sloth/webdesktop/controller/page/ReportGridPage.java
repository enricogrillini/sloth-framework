package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;
import it.eg.sloth.webdesktop.controller.common.grid.ReportGridNavigationInterface;

import java.io.OutputStream;

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
            switch (navigation[0]) {
                case NavigationConst.LOAD:
                    onLoad();
                    return true;
                case NavigationConst.RESET:
                    onReset();
                    return true;
                default:
                    // NOP
            }
        }

        if (navigation.length == 2) {
            Grid<?> grid;
            switch (navigation[0]) {
                case NavigationConst.FIRST_ROW:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onFirstRow(grid);
                    return true;

                case NavigationConst.PREV_PAGE:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onPrevPage(grid);
                    return true;

                case NavigationConst.NEXT_PAGE:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onNextPage(grid);
                    return true;

                case NavigationConst.LAST_ROW:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onLastRow(grid);
                    return true;

                case NavigationConst.EXCEL:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onExcel(grid);
                    return true;

                default:
                    // NOP
            }
        }

        if (navigation.length == 3) {
            Grid<?> grid;
            switch (navigation[0]) {
                case NavigationConst.SORT_ASC:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onSort(grid, navigation[2], SortingRule.SORT_ASC_NULLS_LAST);
                    return true;

                case NavigationConst.SORT_DESC:
                    grid = (Grid<?>) getForm().getElement(navigation[1]);
                    onSort(grid, navigation[2], SortingRule.SORT_DESC_NULLS_LAST);
                    return true;

                default:
                    // NOP
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
        try (OutputStream outputStream = getResponse().getOutputStream()) {
            String fileName = BaseFunction.nvl(grid.getTitle(), getForm().getPageInfo().getTitle()) + FileType.XLSX.getExtension();
            fileName = StringUtil.toFileName(fileName);

            setModelAndView(fileName, FileType.XLSX);

            GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}
