package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.pageinfo.PageStatus;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 *
 * @author Enrico Grillini
 */
class ToolBarWriterTest {

    static final String GRID_NAVIGATION_SIMPLE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationSimple.html");
    static final String GRID_NAVIGATION_SIMPLE_PAGED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationSimple-paged.html");

    static final String GRID_EDITING_SIMPLE_MASTER = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridEditingSimple-master.html");
    static final String GRID_EDITING_SIMPLE_UPDATING = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridEditingSimple-updating.html");

    static final String GRID_NAVIGATION_EDITABLE = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationEditable.html");
    static final String GRID_NAVIGATION_EDITABLE_PAGED = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationEditable-paged.html");

    static final String GRID_NAVIGATION_MASTER_DETAIL_MASTER = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationMasterDetail-master.html");
    static final String GRID_NAVIGATION_MASTER_DETAIL_DETAIL = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationMasterDetail-detail.html");
    static final String GRID_NAVIGATION_MASTER_DETAIL_UPDATING = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridNavigationMasterDetail-updating.html");

    static final String GRID_EDITING_MASTER_DETAIL_MASTER = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridEditingMasterDetail-master.html");
    static final String GRID_EDITING_MASTER_DETAIL_UPDATING = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridEditingMasterDetail-updating.html");

    static final String GRID_EDITING_SUB_MASTER_DETAIL_UPDATING = ResourceUtil.normalizedResourceAsString("snippet-html/toolbar/toolbar-gridEditingSubMasterDetail-updating.html");

    Grid<DataTable<?>> grid;

    @BeforeEach
    void init() {
        grid = new Grid<>("prova", null);
        DataTable<?> table = new Table();
        table.setPageSize(5);

        for (int i = 0; i < 10; i++) {
            table.add();
        }

        grid.setDataSource(table);
    }

    @Test
    void gridNavigationSimpleTest() {
        assertEquals(GRID_NAVIGATION_SIMPLE_PAGED, ToolbarWriter.gridNavigationSimple(grid, "ProvaPage.html", PageStatus.MASTER));

        grid.getDataSource().setPageSize(-1);
        assertEquals(GRID_NAVIGATION_SIMPLE, ToolbarWriter.gridNavigationSimple(grid, "ProvaPage.html", PageStatus.MASTER));
    }

    @Test
    void gridEditingSimpleTest() {
        assertEquals(GRID_EDITING_SIMPLE_MASTER, ToolbarWriter.gridEditingSimple(grid, PageStatus.MASTER));
        assertEquals(GRID_EDITING_SIMPLE_UPDATING, ToolbarWriter.gridEditingSimple(grid, PageStatus.UPDATING));
    }

    @Test
    void gridNavigationEditableTest() {
        assertEquals(GRID_NAVIGATION_EDITABLE_PAGED, ToolbarWriter.gridNavigationEditable(grid, "ProvaPage.html", PageStatus.MASTER));

        grid.getDataSource().setPageSize(-1);
        assertEquals(GRID_NAVIGATION_EDITABLE, ToolbarWriter.gridNavigationEditable(grid, "ProvaPage.html", PageStatus.MASTER));
    }

    @Test
    void gridNavigationMasterDetailTest() {
        assertEquals(GRID_NAVIGATION_MASTER_DETAIL_MASTER, ToolbarWriter.gridNavigationMasterDetail(grid, "ProvaPage.html", PageStatus.MASTER));

        assertEquals(GRID_NAVIGATION_MASTER_DETAIL_DETAIL, ToolbarWriter.gridNavigationMasterDetail(grid, "ProvaPage.html", PageStatus.DETAIL));

        assertEquals(GRID_NAVIGATION_MASTER_DETAIL_UPDATING, ToolbarWriter.gridNavigationMasterDetail(grid, "ProvaPage.html", PageStatus.UPDATING));
    }


    @Test
    void gridEditingMasterDetailTest() {
        assertEquals(GRID_EDITING_MASTER_DETAIL_MASTER, ToolbarWriter.gridEditingMasterDetail(grid, PageStatus.MASTER, false, false, false, false, false));
        assertEquals(GRID_EDITING_MASTER_DETAIL_UPDATING, ToolbarWriter.gridEditingMasterDetail(grid, PageStatus.UPDATING, false, false, false, false, false));
    }

    @Test
    void gridEditingSubMasterDetailTest() {
        assertEquals(StringUtil.EMPTY, ToolbarWriter.gridEditingSubMasterDetail(grid, PageStatus.DETAIL));
        assertEquals(GRID_EDITING_SUB_MASTER_DETAIL_UPDATING, ToolbarWriter.gridEditingSubMasterDetail(grid, PageStatus.UPDATING));
    }

}
