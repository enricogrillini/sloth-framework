package it.eg.sloth.form;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.datasource.table.sort.SortType;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.AutoComplete;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
public class GridTest {

    private Grid<Table> grid;
    private StringDecodeMap decodeMap = new StringDecodeMap("A,2222;B,11111;C,33333");
    Table table;

    @Before
    public void init() {
        grid = new Grid<>("name", "description", "title", null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        decodeMap = new StringDecodeMap("A,2222;B,11111;C,33333");
        table = new Table();

        Row row;
        row = table.add();
        row.setString("campo", "A");
        row.setString("descrizione", decodeMap.decode("A"));

        row = table.add();
        row.setString("campo", "B");
        row.setString("descrizione", decodeMap.decode("B"));

        row = table.add();
        row.setString("campo", "C");
        row.setString("descrizione", decodeMap.decode("C"));
    }

    @Test
    public void gridTest() {
        assertFalse(grid.isBackButtonHidden());
        assertFalse(grid.isSelectButtonHidden());
        assertFalse(grid.isFirstButtonHidden());
        assertFalse(grid.isPrevPageButtonHidden());
        assertFalse(grid.isPrevButtonHidden());
        assertFalse(grid.isDetailButtonHidden());
        assertFalse(grid.isNextButtonHidden());
        assertFalse(grid.isNextPageButtonHidden());
        assertFalse(grid.isLastButtonHidden());
        assertFalse(grid.isInsertButtonHidden());
        assertFalse(grid.isDeleteButtonHidden());
        assertFalse(grid.isUpdateButtonHidden());
        assertFalse(grid.isCommitButtonHidden());
        assertFalse(grid.isRollbackButtonHidden());
    }


    @Test
    public void gridSortTest() {
        grid.setDataSource(table);

        AutoComplete<String> autoComplete = AutoComplete.<String>builder()
                .name("campo")
                .description("Descrizione campo")
                .dataType(DataTypes.STRING)
                .decodeMap(decodeMap)
                .build();

        grid.addChild(autoComplete);

        grid.orderBy("campo", SortType.SORT_ASC_NULLS_LAST);
        assertEquals("A", ((DataRow) grid.getDataSource().getRows().get(0)).getString("campo"));
        assertEquals("B", ((DataRow) grid.getDataSource().getRows().get(1)).getString("campo"));
        assertEquals("C", ((DataRow) grid.getDataSource().getRows().get(2)).getString("campo"));

        grid.orderBy("campo", SortType.SORT_DESC_NULLS_LAST);
        assertEquals("C", ((DataRow) grid.getDataSource().getRows().get(0)).getString("campo"));
        assertEquals("B", ((DataRow) grid.getDataSource().getRows().get(1)).getString("campo"));
        assertEquals("A", ((DataRow) grid.getDataSource().getRows().get(2)).getString("campo"));
    }


    @Test
    public void gridOrderByAliasTest() {
        grid.setDataSource(table);

        AutoComplete<String> autoComplete = AutoComplete.<String>builder()
                .name("campo")
                .description("Descrizione campo")
                .dataType(DataTypes.STRING)
                .decodeMap(decodeMap)
                .orderByAlias("descrizione")
                .build();

        grid.addChild(autoComplete);


        grid.orderBy("campo", SortType.SORT_ASC_NULLS_LAST);
        assertEquals("B", ((DataRow) grid.getDataSource().getRows().get(0)).getString("campo"));
        assertEquals("A", ((DataRow) grid.getDataSource().getRows().get(1)).getString("campo"));
        assertEquals("C", ((DataRow) grid.getDataSource().getRows().get(2)).getString("campo"));

        grid.orderBy("campo", SortType.SORT_DESC_NULLS_LAST);
        assertEquals("C", ((DataRow) grid.getDataSource().getRows().get(0)).getString("campo"));
        assertEquals("A", ((DataRow) grid.getDataSource().getRows().get(1)).getString("campo"));
        assertEquals("B", ((DataRow) grid.getDataSource().getRows().get(2)).getString("campo"));
    }

}
