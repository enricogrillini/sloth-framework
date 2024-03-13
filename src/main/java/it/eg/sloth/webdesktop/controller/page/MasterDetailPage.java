package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.controller.common.grid.MasterDetailGridNavigationInterface;
import it.eg.sloth.webdesktop.controller.common.tab.BaseTabSelection;
import lombok.extern.slf4j.Slf4j;

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
 * Fornisce l'implementazione di una griglia NON editabile master detail.
 * Gestisce i metodi: onLoad, onGoToRecord, onFirstRow, onPrevPage, onPrev,
 * onNext, onNextPage, onLastRow, onElenco, onExcel
 *
 * @param <F>
 * @param <G>
 * @author Enrico Grillini
 */
@Slf4j
public abstract class MasterDetailPage<F extends Form, G extends Grid<?>> extends SimplePage<F> implements MasterDetailGridNavigationInterface<F, G>, BaseTabSelection<F> {

    protected MasterDetailPage() {
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
            } else if (NavigationConst.ELENCO.equals(navigation[0])) {
                onElenco();
                return true;
            }
        }

        if (navigation.length == 2) {
            Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);

            if (NavigationConst.FIRST_ROW.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onFirstRow();
                } else {
                    onSubFirstRow(grid);
                }
                return true;
            } else if (NavigationConst.PREV_PAGE.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onPrevPage();
                } else {
                    onSubPrevPage(grid);
                }
                return true;
            } else if (NavigationConst.PREV.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onPrev();
                } else {
                    onSubPrev(grid);
                }
                return true;
            } else if (NavigationConst.NEXT.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onNext();
                } else {
                    onSubNext(grid);
                }
                return true;
            } else if (NavigationConst.NEXT_PAGE.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onNextPage();
                } else {
                    onSubNextPage(grid);
                }
                return true;
            } else if (NavigationConst.LAST_ROW.equals(navigation[0])) {
                if (grid == getGrid()) {
                    onLastRow();
                } else {
                    onSubLastRow(grid);
                }
                return true;
            }
        }

        if (navigation.length == 3) {
            if (NavigationConst.ROW.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);

                if (grid == getGrid()) {
                    onGoToRecord(Integer.valueOf(navigation[2]));
                } else {
                    onSubGoToRecord(grid, Integer.valueOf(navigation[2]));
                }

                return true;
            } else if (NavigationConst.SORT_ASC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortType.SORT_ASC_NULLS_LAST);
                return true;
            } else if (NavigationConst.SORT_DESC.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onSort(grid, navigation[2], SortType.SORT_DESC_NULLS_LAST);
                return true;
            } else if (NavigationConst.TAB.equals(navigation[0])) {
                onSelectTab(navigation[1], navigation[2]);
                return true;
            }
        }

        return false;
    }

    protected abstract String getJspMasterName();

    protected abstract String getJspDetailName();

    @Override
    protected String getJspName() {
        return getForm().getPageInfo().getPageStatus() == PageStatus.MASTER ? getJspMasterName() : getJspDetailName();
    }

    @Override
    public void onInit() throws Exception {
        getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
        execInit();
    }

    @Override
    public void onLoad() throws Exception {
        getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
        MasterDetailGridNavigationInterface.super.onLoad();
    }

    @Override
    public void onReset() throws Exception {
        getForm().getPageInfo().setPageStatus(PageStatus.MASTER);
        MasterDetailGridNavigationInterface.super.onReset();
    }

    @Override
    public void onGoToRecord(int row) throws Exception {
        if (getGrid().getDataSource().size() > row) {
            getGrid().getDataSource().setCurrentRow(row);
            execLoadDetail();
            getForm().getPageInfo().setViewModality(ViewModality.VIEW);
            getForm().getPageInfo().setPageStatus(PageStatus.DETAIL);
        }
    }

    @Override
    public void onSubGoToRecord(Grid<?> grid, int row) throws Exception {
        grid.getDataSource().setCurrentRow(row);
        execLoadSubDetail(grid);
    }

    @Override
    public void onFirstRow() throws Exception {
        getGrid().getDataSource().first();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubFirstRow(Grid<?> grid) throws Exception {
        grid.getDataSource().first();
        execLoadSubDetail(grid);
    }

    @Override
    public void onPrevPage() throws Exception {
        getGrid().getDataSource().prevPage();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubPrevPage(Grid<?> grid) throws Exception {
        grid.getDataSource().prevPage();
        execLoadSubDetail(grid);
    }

    @Override
    public void onPrev() throws Exception {
        getGrid().getDataSource().prev();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubPrev(Grid<?> grid) throws Exception {
        grid.getDataSource().prev();
        execLoadSubDetail(grid);
    }

    @Override
    public void onNext() throws Exception {
        getGrid().getDataSource().next();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubNext(Grid<?> grid) throws Exception {
        grid.getDataSource().next();
        execLoadSubDetail(grid);
    }

    @Override
    public void onNextPage() throws Exception {
        getGrid().getDataSource().nextPage();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubNextPage(Grid<?> grid) throws Exception {
        grid.getDataSource().nextPage();
        execLoadSubDetail(grid);
    }

    @Override
    public void onLastRow() throws Exception {
        getGrid().getDataSource().last();
        if (getForm().getPageInfo().getPageStatus() == PageStatus.DETAIL) {
            execLoadDetail();
        }
    }

    @Override
    public void onSubLastRow(Grid<?> grid) throws Exception {
        grid.getDataSource().last();
        execLoadSubDetail(grid);
    }

}
