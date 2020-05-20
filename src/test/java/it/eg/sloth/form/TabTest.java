package it.eg.sloth.form;

import it.eg.sloth.form.skipper.Skipper;
import it.eg.sloth.form.tabsheet.Tab;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Project: gilda-ce
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
public class TabTest {

    @Test
    public void tabTest() {
        Tab tab = new Tab("prova", "prova", false, false);
        assertFalse(tab.isHidden());
        assertFalse(tab.isDisabled());

        tab = new Tab("prova", "prova", true, true);
        assertTrue(tab.isHidden());
        assertTrue(tab.isDisabled());

        tab = new Tab("prova", "prova", null, null);
        assertFalse(tab.isHidden());
        assertFalse(tab.isDisabled());
    }
}
