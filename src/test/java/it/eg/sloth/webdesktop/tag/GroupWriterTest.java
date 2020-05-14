package it.eg.sloth.webdesktop.tag;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;

import org.junit.Test;

import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

public class GroupWriterTest {

    private static final String OPEN_GROUP = "<filedset>{0}";
    private static final String CLOSE_GROUP = "</filedset>";

    private static final String OPEN_ROW = "<div class=\"row form-group\">";
    private static final String CLOSE_ROW = "</div>";

    private static final String OPEN_CELL = "<div class=\"col-2\">";
    private static final String CLOSE_CELL = "</div>";

    @Test
    public void openGroupTest() throws BusinessException {
        assertEquals(MessageFormat.format(OPEN_GROUP, ""), GroupWriter.openGroup(null));
        assertEquals(MessageFormat.format(OPEN_GROUP, "<legend>prova</legend>"), GroupWriter.openGroup("prova"));
    }

    @Test
    public void closeGroupTest() throws BusinessException {
        assertEquals(CLOSE_GROUP, GroupWriter.closeGroup());
    }

    @Test
    public void openRowTest() throws BusinessException {
        assertEquals(OPEN_ROW, GroupWriter.openRow());
    }

    @Test
    public void closeRowTest() throws BusinessException {
        assertEquals(CLOSE_ROW, GroupWriter.closeRow());
    }

    @Test
    public void openCellTest() throws BusinessException {
        assertEquals(OPEN_CELL, GroupWriter.openCell(null, null, null));
        assertEquals(OPEN_CELL, GroupWriter.openCell(null, null, "10%"));
        assertEquals(OPEN_CELL, GroupWriter.openCell("", "", ""));
    }

    @Test
    public void closeCellTest() {
        assertEquals(CLOSE_CELL, GroupWriter.closeCell());
    }

}
