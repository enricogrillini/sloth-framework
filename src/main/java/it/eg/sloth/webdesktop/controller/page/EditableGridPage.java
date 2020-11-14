package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.db.DataManager;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.Form;
import it.eg.sloth.form.NavigationConst;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.FileType;
import it.eg.sloth.framework.utility.xlsx.GridXlsxWriter;
import it.eg.sloth.webdesktop.controller.common.editable.FullEditingInterface;
import it.eg.sloth.webdesktop.controller.common.grid.EditableGridNavigationInterface;

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
 * Fornisce l'implementazione di una griglia editabile. Rispetto al
 * EditablePageController aggiunge: - aggiunge la gestione degli eventi
 * onInsert, onDelete, onGotoRecord
 *
 * @param <F>
 * @param <G>
 * @author Enrico Grillini
 */
public abstract class EditableGridPage<F extends Form, G extends Grid<?>> extends EditablePage<F> implements EditableGridNavigationInterface, FullEditingInterface {

    public EditableGridPage() {
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
            if (NavigationConst.INSERT.equals(navigation[0])) {
                onInsert();
                return true;
            } else if (NavigationConst.DELETE.equals(navigation[0])) {
                onDelete();
                return true;
            } else if (NavigationConst.UPDATE.equals(navigation[0])) {
                onUpdate();
                return true;
            } else if (NavigationConst.FIRST_ROW.equals(navigation[0])) {
                onFirstRow();
                return true;
            } else if (NavigationConst.PREV_PAGE.equals(navigation[0])) {
                onPrevPage();
                return true;
            } else if (NavigationConst.PREV.equals(navigation[0])) {
                onPrev();
                return true;
            } else if (NavigationConst.NEXT.equals(navigation[0])) {
                onNext();
                return true;
            } else if (NavigationConst.NEXT_PAGE.equals(navigation[0])) {
                onNextPage();
                return true;
            } else if (NavigationConst.LAST_ROW.equals(navigation[0])) {
                onLastRow();
                return true;
            } else if (NavigationConst.EXCEL.equals(navigation[0])) {
                Grid<?> grid = (Grid<?>) getForm().getElement(navigation[1]);
                onExcel(grid);
                return true;
            }
        }

        if (navigation.length == 3) {
            if (NavigationConst.ROW.equals(navigation[0])) {
                onGoToRecord(Integer.valueOf(navigation[2]));
                return true;
            } else if (NavigationConst.SORT_ASC.equals(navigation[0])) {
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

    protected abstract G getGrid();

    public boolean execPostDetail(boolean validate) throws Exception {
        getGrid().post(getWebRequest());
        if (validate && getGrid().validate(getMessageList())) {
            getGrid().copyToDataSource(getGrid().getDataSource());
            return true;
        } else {
            return !validate;
        }
    }

    public boolean execPreMove() throws Exception {
        if (ViewModality.VIEW_VISUALIZZAZIONE.equals(getForm().getPageInfo().getViewModality())) {
            return true;
        } else {
            return execPostDetail(true);
        }
    }

    public void execPostMove() throws Exception {
        getGrid().copyFromDataSource(getGrid().getDataSource());
    }

    public boolean execGoToRecord(int record) throws Exception {
        if (ViewModality.VIEW_VISUALIZZAZIONE.equals(getForm().getPageInfo().getViewModality()) || execPostDetail(true)) {
            getGrid().getDataSource().setCurrentRow(record);
            getGrid().copyFromDataSource(getGrid().getDataSource());
        }
        return true;
    }

    public boolean execInsert() throws Exception {
        if (ViewModality.VIEW_VISUALIZZAZIONE.equals(getForm().getPageInfo().getViewModality()) || execPostDetail(true)) {
            getGrid().clearData();
            getGrid().getDataSource().add();
        }
        return true;
    }

    @Override
    public boolean execUpdate() throws Exception {
        getGrid().copyFromDataSource(getGrid().getDataSource());
        return true;
    }

    @Override
    public boolean execDelete() throws Exception {
        getGrid().getDataSource().remove();
        getGrid().copyFromDataSource(getGrid().getDataSource());
        return true;
    }

    @Override
    public boolean execCommit() throws Exception {
        if (getGrid().size() <= 0 || execPostDetail(true)) {
            DataManager.saveFirstToLast(getForm());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onInsert() throws Exception {
        if (execInsert()) {
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    @Override
    public void onDelete() throws Exception {
        if (execDelete()) {
            getForm().getPageInfo().setViewModality(ViewModality.VIEW_MODIFICA);
        }
    }

    @Override
    public void onGoToRecord(int record) throws Exception {
        if (execPreMove() && getGrid().getDataSource().size() > record) {
            getGrid().getDataSource().setCurrentRow(record);
            execPostMove();
        }
    }

    public void onLoad() throws Exception {
        execLoad();
    }

    public void onReset() throws Exception {
        execReset();
    }

    @Override
    public void onFirstRow() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().first();
            execPostMove();
        }
    }

    @Override
    public void onPrevPage() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().prevPage();
            execPostMove();
        }
    }

    @Override
    public void onPrev() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().prev();
            execPostMove();
        }
    }

    @Override
    public void onNext() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().next();
            execPostMove();
        }
    }

    @Override
    public void onNextPage() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().nextPage();
            execPostMove();
        }
    }

    @Override
    public void onLastRow() throws Exception {
        if (execPreMove()) {
            getGrid().getDataSource().last();
            execPostMove();
        }
    }

    @Override
    public void onSort(Grid<?> grid, String fieldName, int sortType) throws Exception {
        grid.orderBy(fieldName, sortType);
    }

    @Override
    public void onExcel(Grid<?> grid) throws Exception {
        try (OutputStream outputStream = getResponse().getOutputStream(); GridXlsxWriter gridXlsxWriter = new GridXlsxWriter(true, grid);) {
            String fileName = BaseFunction.nvl(grid.getTitle(), grid.getName()) + FileType.XLSX.getExtension();
            fileName = StringUtil.toFileName(fileName);

            setModelAndView(fileName, FileType.XLSX);
            gridXlsxWriter.getWorkbook().write(outputStream);
        }
    }

}
