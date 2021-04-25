package it.eg.sloth.webdesktop.tag;


import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
 *
 * @author Enrico Grillini
 */
class ToolBarButtonWriterTest {

    static final String EXCEL = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-excel.html");
    static final String EXCEL_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-excel-disabled.html");

    static final String ELENCO = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-elenco.html");
    static final String ELENCO_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-elenco-disabled.html");

    static final String FIRST_ROW = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-firstRow.html");
    static final String FIRST_ROW_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-firstRow-disabled.html");

    static final String PREV_PAGE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-prevPage.html");
    static final String PREV_PAGE_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-prevPage-disabled.html");

    static final String PREV = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-prev.html");
    static final String PREV_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-prev-disabled.html");

    static final String NEXT = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-next.html");
    static final String NEXT_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-next-disabled.html");

    static final String NEXT_PAGE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-nextPage.html");
    static final String NEXT_PAGE_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-nextPage-disabled.html");

    static final String LAST_ROW = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-lastRow.html");
    static final String LAST_ROW_DISABLED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-lastRow-disabled.html");

    static final String INSERT = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-insert.html");
    static final String DELETE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-delete.html");
    static final String UPDATE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-update.html");

    static final String COMMIT = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-commit.html");
    static final String ROLLBACK = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar-button/toolbar-rollback.html");

    Grid<DataTable<?>> grid;

    @BeforeEach
    void init() {
        grid = new Grid<>("prova", null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        DataTable<?> table = new Table();
        table.setPageSize(5);

        for (int i = 0; i < 10; i++) {
            table.add();
        }

        grid.setDataSource(table);
    }

    @Test
    void excelButtonTest() {
        assertEquals(EXCEL, ToolbarWriter.excelButton("Master", "ProvaPage.html", false));
        assertEquals(EXCEL_DISABLED, ToolbarWriter.excelButton("Master", "ProvaPage.html", true));
    }

    @Test
    void elencoButtonTest() {
        assertEquals(ELENCO, ToolbarWriter.elencoButton(false));
        assertEquals(ELENCO_DISABLED, ToolbarWriter.elencoButton(true));
    }

    @Test
    void firstRowButtonTest() {
        grid.getDataSource().last();
        assertEquals(FIRST_ROW, ToolbarWriter.firstRowButton(grid, false));

        grid.getDataSource().first();
        assertEquals(FIRST_ROW_DISABLED, ToolbarWriter.firstRowButton(grid, false));

        // Hidden
        grid.setFirstButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.firstRowButton(grid, false));
    }

    @Test
    void prevPageButtonTest() {
        grid.getDataSource().last();
        assertEquals(PREV_PAGE, ToolbarWriter.prevPageButton(grid, false));

        grid.getDataSource().first();
        assertEquals(PREV_PAGE_DISABLED, ToolbarWriter.prevPageButton(grid, false));

        // Hidden
        grid.setPrevPageButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.prevPageButton(grid, false));
    }

    @Test
    void prevButtonTest() {
        grid.getDataSource().last();
        assertEquals(PREV, ToolbarWriter.prevButton(grid, false));

        grid.getDataSource().first();
        assertEquals(PREV_DISABLED, ToolbarWriter.prevButton(grid, false));

        // Hidden
        grid.setPrevButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.prevButton(grid, false));
    }

    @Test
    void nextButtonTest() {
        grid.getDataSource().first();
        assertEquals(NEXT, ToolbarWriter.nextButton(grid, false));

        grid.getDataSource().last();
        assertEquals(NEXT_DISABLED, ToolbarWriter.nextButton(grid, false));

        // Hidden
        grid.setNextButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.nextButton(grid, false));
    }

    @Test
    void nextPageButtonTest() {
        grid.getDataSource().first();
        assertEquals(NEXT_PAGE, ToolbarWriter.nextPageButton(grid, false));

        grid.getDataSource().last();
        assertEquals(NEXT_PAGE_DISABLED, ToolbarWriter.nextPageButton(grid, false));

        // Hidden
        grid.setNextPageButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.nextPageButton(grid, false));
    }

    @Test
    void lastRowButtonTest() {
        grid.getDataSource().first();
        assertEquals(LAST_ROW, ToolbarWriter.lastRowButton(grid, false));

        grid.getDataSource().last();
        assertEquals(LAST_ROW_DISABLED, ToolbarWriter.lastRowButton(grid, false));

        // Hidden
        grid.setLastButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.lastRowButton(grid, false));
    }


    @Test
    void insertButtonTest() {
        // Grid
        assertEquals(INSERT, ToolbarWriter.insertButton(grid));

        // Hidden
        grid.setInsertButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.insertButton(grid));
    }

    @Test
    void deleteButtonTest() {
        // Grid
        assertEquals(DELETE, ToolbarWriter.deleteButton(grid));

        // Hidden
        grid.setDeleteButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.deleteButton(grid));
    }

    @Test
    void updateButtonTest() {
        // Grid
        assertEquals(UPDATE, ToolbarWriter.updateButton(grid));

        // Hidden
        grid.setUpdateButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.updateButton(grid));

    }

    @Test
    void commitButtonTest() {
        assertEquals(COMMIT, ToolbarWriter.commitButton());
    }

    @Test
    void rollbackButtonTest() {
        assertEquals(ROLLBACK, ToolbarWriter.rollbackButton());
    }


}
