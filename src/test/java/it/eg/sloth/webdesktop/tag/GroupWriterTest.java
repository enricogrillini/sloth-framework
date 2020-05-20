package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Test;

import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

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
 *
 */
public class GroupWriterTest {

    private static final String OPEN_GROUP = "<filedset>{0}";
    private static final String CLOSE_GROUP = "</filedset>";

    private static final String OPEN_ROW = "<div class=\"row form-group\">";
    private static final String CLOSE_ROW = "</div>";

    private static final String OPEN_CELL = "<div class=\"col-2\">";
    private static final String CLOSE_CELL = "</div>";

    @Test
    public void openGroupTest() throws FrameworkException {
        assertEquals(MessageFormat.format(OPEN_GROUP, ""), GroupWriter.openGroup(null));
        assertEquals(MessageFormat.format(OPEN_GROUP, "<legend>prova</legend>"), GroupWriter.openGroup("prova"));
    }

    @Test
    public void closeGroupTest() throws FrameworkException {
        assertEquals(CLOSE_GROUP, GroupWriter.closeGroup());
    }

    @Test
    public void openRowTest() throws FrameworkException {
        assertEquals(OPEN_ROW, GroupWriter.openRow());
    }

    @Test
    public void closeRowTest() throws FrameworkException {
        assertEquals(CLOSE_ROW, GroupWriter.closeRow());
    }

    @Test
    public void openCellTest() throws FrameworkException {
        assertEquals(OPEN_CELL, GroupWriter.openCell(null, null, null));
        assertEquals(OPEN_CELL, GroupWriter.openCell(null, null, "10%"));
        assertEquals(OPEN_CELL, GroupWriter.openCell("", "", ""));
    }

    @Test
    public void closeCellTest() {
        assertEquals(CLOSE_CELL, GroupWriter.closeCell());
    }

}
