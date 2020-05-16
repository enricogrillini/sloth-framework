package it.eg.sloth.form;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import it.eg.sloth.form.grid.Grid;

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
