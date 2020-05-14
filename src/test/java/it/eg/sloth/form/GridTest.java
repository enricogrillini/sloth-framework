package it.eg.sloth.form;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import it.eg.sloth.form.grid.Grid;

public class GridTest {

    @Test
    public void gridTest() {
        Grid grid = new Grid("name", "description", "title", null, null, null, null, null, null, null, null, null, null, null, null, null, null);
        
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
}
