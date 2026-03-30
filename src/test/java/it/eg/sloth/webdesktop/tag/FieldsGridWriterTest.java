package it.eg.sloth.webdesktop.tag;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.impl.Input;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.utility.grid.GridUtil;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.field.writer.FieldsGridWriter;
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
class FieldsGridWriterTest {

    private static final String HEADER_ROW = ResourceUtil.normalizedResourceAsString("snippet-html/fields-grid/header-row.html");

    private static final String ROWS_EDIT_MODE = ResourceUtil.normalizedResourceAsString("snippet-html/fields-grid/rows_edit-mode.html");


    Table table;
    Grid<Table> grid;

    @BeforeEach
    void init() {
        table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setString("campo3", "Lorem ipsum A");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setString("campo3", "Lorem ipsum B");

        grid = new Grid<>("provaGrid", null);
        grid.addChild(new Input<String>("campo1", "campo1", DataTypes.STRING));
        grid.addChild(new Input<String>("campo2", "campo2", DataTypes.STRING));
        grid.setDataSource(table);
    }

    @Test
    void headerRow() throws FrameworkException {
        Fields<?> fields = new Fields("Prova");
        GridUtil.copyFromDataSourceGridToFields(grid, fields);

        assertEquals(HEADER_ROW, FieldsGridWriter.headerRow(fields));
    }

    @Test
    void rows_EditMode() throws FrameworkException {
        Fields<?> fields = new Fields("Prova");
        GridUtil.copyFromDataSourceGridToFields(grid, fields);

        assertEquals(ROWS_EDIT_MODE, FieldsGridWriter.rows(fields, ViewModality.EDIT));
    }

}
