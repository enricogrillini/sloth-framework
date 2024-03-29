package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.CheckBox;
import it.eg.sloth.form.fields.field.impl.Switch;
import it.eg.sloth.framework.common.casting.DataTypes;
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
class SwitchTest {

    @Test
    void buttonTest() {
        Switch<String> field = new Switch<>("name", "description", DataTypes.STRING);
        assertEquals(CheckBox.DEFAULT_VAL_CHECKED, field.getValChecked());
        assertEquals(CheckBox.DEFAULT_VAL_UN_CHECKED, field.getValUnChecked());

        field.setValChecked("1");
        field.setValUnChecked("2");
        assertEquals("1", field.getValChecked());
        assertEquals("2", field.getValUnChecked());
    }


}
