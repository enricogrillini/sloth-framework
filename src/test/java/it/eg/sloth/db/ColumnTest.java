package it.eg.sloth.db;

import it.eg.sloth.db.datasource.row.column.Column;
import it.eg.sloth.form.fields.field.base.InputField;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Types;

import static org.junit.jupiter.api.Assertions.*;

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
class ColumnTest {


    @Test
    void inputFieldTest() {
        InputField<BigDecimal> bigDecimalField = new Column("ID", "Id", true, false, 10, Types.INTEGER).getInputField();
        assertEquals("id", bigDecimalField.getName());
        assertEquals("Id", bigDecimalField.getDescription());
        assertEquals(DataTypes.INTEGER, bigDecimalField.getDataType());
        assertEquals(ViewModality.AUTO, bigDecimalField.getViewModality());
        assertTrue(bigDecimalField.isRequired());

        InputField<String> inputField = new Column("Testo", "Testo", false, true, 10, Types.VARCHAR).getInputField();
        assertEquals("testo", inputField.getName());
        assertEquals("Testo", inputField.getDescription());
        assertEquals(DataTypes.STRING, inputField.getDataType());
        assertEquals(ViewModality.AUTO, inputField.getViewModality());
        assertFalse(inputField.isRequired());

        InputField<Timestamp> timestampField = new Column("Data", "Data", false, false, null, Types.DATE).getInputField();
        assertEquals("data", timestampField.getName());
        assertEquals("Data", timestampField.getDescription());
        assertEquals(DataTypes.DATE, timestampField.getDataType());
        assertEquals(ViewModality.AUTO, timestampField.getViewModality());
        assertTrue(timestampField.isRequired());
    }

    @Test
    void comboBoxTest() {
        ComboBox<BigDecimal> bigDecimalField = new Column("ID", "Id", true, false, 10, Types.INTEGER).getComboBox();
        assertEquals("id", bigDecimalField.getName());
        assertEquals("Id", bigDecimalField.getDescription());
        assertEquals(DataTypes.INTEGER, bigDecimalField.getDataType());
        assertEquals(ViewModality.AUTO, bigDecimalField.getViewModality());
        assertTrue(bigDecimalField.isRequired());
    }

}

