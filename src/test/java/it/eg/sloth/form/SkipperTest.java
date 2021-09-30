package it.eg.sloth.form;

import it.eg.sloth.form.skipper.Skipper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
class SkipperTest {

    @Test
    void skipperTest() {
        Skipper skipper = new Skipper("prova", false);
        assertFalse(skipper.isSkipBody());

        skipper = new Skipper("prova", true);
        assertTrue(skipper.isSkipBody());

        skipper = new Skipper("prova", null);
        assertFalse(skipper.isSkipBody());
    }
}
