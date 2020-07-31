package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Before;
import org.junit.Test;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.webdesktop.tag.form.toolbar.writer.ToolbarWriter;

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
public class ToolBarWriterTest {

    static final String ELENCO = "<button name=\"navigationprefix___elenco\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ritorna all''elenco\"><i class=\"fas fa-table\"></i> Elenco</button>";

    static final String FIRST_ROW_GRID = "<button name=\"navigationprefix___firstpage___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Primo record\"><i class=\"fas fa-fast-backward\"></i></button>";
    static final String PREV_PAGE_GRID = "<button name=\"navigationprefix___prevpage___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina precedende\"><i class=\"fas fa-backward\"></i></button>";
    static final String PREV_GRID = "<button name=\"navigationprefix___prev___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record precedende\"><i class=\"fas fa-step-backward\"></i></button>";

    static final String NEXT_GRID = "<button name=\"navigationprefix___next___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Record successivo\"><i class=\"fas fa-step-forward\"></i></button>";
    static final String NEXT_PAGE_GRID = "<button name=\"navigationprefix___nextpage___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Pagina sucessiva\"><i class=\"fas fa-forward\"></i></button>";
    static final String LAST_ROW_GRID = "<button name=\"navigationprefix___lastrow___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Ultimo record\"><i class=\"fas fa-fast-forward\"></i></button>";

    static final String INSERT_GRID = "<button name=\"navigationprefix___insert___prova\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Inserisci record\"><i class=\"fas fa-plus\"></i> Inserisci</button>";
    static final String DELETE_GRID = "<button name=\"navigationprefix___delete___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Elimina il record corrente\"><i class=\"fas fa-minus\"></i> Elimina</button>";
    static final String UPDATE_GRID = "<button name=\"navigationprefix___update___prova\"{0} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Modifica il record corrente\"><i class=\"fas fa-pencil-alt\"></i> Modifica</button>";

    static final String COMMIT = "<button name=\"navigationprefix___commit\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Salva\"><i class=\"fas fa-save\"></i> Salva</button>";
    static final String ROLLBACK = "<button name=\"navigationprefix___rollback\" type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Annulla modifiche\"><i class=\"fas fa-undo-alt\"></i> Annulla</button>";

    static final String UPDATE = "<button name=\"navigationprefix___update___{0}\"{1} type=\"submit\" class=\"btn btn-link btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Modifica il record corrente\"><i class=\"fas fa-pencil-alt\"></i> Modifica</button>";


    Grid<DataTable<?>> grid;

    @Before
    public void init() {
        grid = new Grid<>("prova", null, null, false, false, false, false, false, false, false, false, false, false, false, false, false, false);
        DataTable<?> table = new Table();
        table.setPageSize(5);

        for (int i = 0; i < 10; i++) {
            table.add();
        }

        grid.setDataSource(table);
    }

    @Test
    public void elencoButtonTest() {
        assertEquals(MessageFormat.format(ELENCO, ""), ToolbarWriter.elencoButton(false));
        assertEquals(MessageFormat.format(ELENCO, " disabled=\"\""), ToolbarWriter.elencoButton(true));
    }

    @Test
    public void firstRowButtonTest() {
        grid.getDataSource().last();
        assertEquals(MessageFormat.format(FIRST_ROW_GRID, ""), ToolbarWriter.firstRowButton(grid, false));

        grid.getDataSource().first();
        assertEquals(MessageFormat.format(FIRST_ROW_GRID, " disabled=\"\""), ToolbarWriter.firstRowButton(grid, false));

        // Hidden
        grid.setFirstButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.firstRowButton(grid, false));
    }

    @Test
    public void prevPageButtonTest() {
        grid.getDataSource().last();
        assertEquals(MessageFormat.format(PREV_PAGE_GRID, ""), ToolbarWriter.prevPageButton(grid, false));

        grid.getDataSource().first();
        assertEquals(MessageFormat.format(PREV_PAGE_GRID, " disabled=\"\""), ToolbarWriter.prevPageButton(grid, false));

        // Hidden
        grid.setPrevPageButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.prevPageButton(grid, false));
    }

    @Test
    public void prevButtonTest() {
        grid.getDataSource().last();
        assertEquals(MessageFormat.format(PREV_GRID, ""), ToolbarWriter.prevButton(grid, false));

        grid.getDataSource().first();
        assertEquals(MessageFormat.format(PREV_GRID, " disabled=\"\""), ToolbarWriter.prevButton(grid, false));

        // Hidden
        grid.setPrevButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.prevButton(grid, false));
    }

    @Test
    public void nextButtonTest() {
        grid.getDataSource().first();
        assertEquals(MessageFormat.format(NEXT_GRID, ""), ToolbarWriter.nextButton(grid, false));

        grid.getDataSource().last();
        assertEquals(MessageFormat.format(NEXT_GRID, " disabled=\"\""), ToolbarWriter.nextButton(grid, false));

        // Hidden
        grid.setNextButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.nextButton(grid, false));
    }

    @Test
    public void nextPageButtonTest() {
        grid.getDataSource().first();
        assertEquals(MessageFormat.format(NEXT_PAGE_GRID, ""), ToolbarWriter.nextPageButton(grid, false));

        grid.getDataSource().last();
        assertEquals(MessageFormat.format(NEXT_PAGE_GRID, " disabled=\"\""), ToolbarWriter.nextPageButton(grid, false));

        // Hidden
        grid.setNextPageButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.nextPageButton(grid, false));
    }

    @Test
    public void lastRowButtonTest() {
        grid.getDataSource().first();
        assertEquals(MessageFormat.format(LAST_ROW_GRID, ""), ToolbarWriter.lastRowButton(grid, false));

        grid.getDataSource().last();
        assertEquals(MessageFormat.format(LAST_ROW_GRID, " disabled=\"\""), ToolbarWriter.lastRowButton(grid, false));

        // Hidden
        grid.setLastButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.lastRowButton(grid, false));
    }

    @Test
    public void commitButtonTest() {
        assertEquals(COMMIT, ToolbarWriter.commitButton());
    }

    @Test
    public void rollbackButtonTest() {
        assertEquals(ROLLBACK, ToolbarWriter.rollbackButton());
    }

    @Test
    public void insertButtonTest() {
        // Grid
        assertEquals(MessageFormat.format(INSERT_GRID, ""), ToolbarWriter.insertButton(grid));

        // Hidden
        grid.setInsertButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.insertButton(grid));
    }

    @Test
    public void deleteButtonTest() {
        // Grid
        assertEquals(MessageFormat.format(DELETE_GRID, ""), ToolbarWriter.deleteButton(grid));

        // Hidden
        grid.setDeleteButtonHidden(true);
        assertEquals(StringUtil.EMPTY, ToolbarWriter.deleteButton(grid));
    }

    @Test
    public void updateButtonTest() {
        // Grid
        assertEquals(MessageFormat.format(UPDATE_GRID, ""), ToolbarWriter.updateButton(grid));

        // Altro
        assertEquals(StringUtil.EMPTY, ToolbarWriter.updateButton("prova", true, true));
        assertEquals(MessageFormat.format(UPDATE, "prova", StringUtil.EMPTY), ToolbarWriter.updateButton("prova", false, false));
        assertEquals(MessageFormat.format(UPDATE, "prova", " disabled=\"\""), ToolbarWriter.updateButton("prova", false, true));
    }

}
