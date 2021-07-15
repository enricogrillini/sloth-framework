package it.eg.sloth.form;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
class MultipleAutoCompleteTest {

    MultipleAutoComplete<BigDecimal> multipleAutoComplete;

    @BeforeEach
    public void init() {
        multipleAutoComplete = new MultipleAutoComplete<>("Numero", "Numero", DataTypes.INTEGER);
        multipleAutoComplete.setDecodeMap(TestFactory.getBaseDecodeMap());
    }

    @Test
    void baseTest() throws FrameworkException {
        List<BigDecimal> values = new ArrayList<>();
        values.add(BigDecimal.valueOf(1));
        values.add(BigDecimal.valueOf(2));

        multipleAutoComplete.setValue(values);

        assertEquals("1,2", multipleAutoComplete.getData());
        assertEquals("Valore A,Valore B", multipleAutoComplete.getDecodedText());
    }

    @Test
    void requestOkTest() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A,Valore B,Valore C"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);

        multipleAutoComplete.post(webRequest);

        assertEquals("1,2,3", multipleAutoComplete.getData());
        assertEquals("Valore A,Valore B,Valore C", multipleAutoComplete.getDecodedText());

        MessageList messageList = new MessageList();
        multipleAutoComplete.validate(messageList);
        assertTrue(messageList.isEmpty());
    }

    @Test
    void requestKoTest() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A,Valore B,Valore X"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);
        multipleAutoComplete.post(webRequest);

        assertEquals("", multipleAutoComplete.getData());
        assertEquals("Valore A,Valore B,Valore X", multipleAutoComplete.getDecodedText());

        MessageList messageList = new MessageList();
        multipleAutoComplete.validate(messageList);
        assertEquals("Il campo Numero contiene valori non validi", messageList.getMessagesDescription());
        assertFalse(messageList.isEmpty());
    }

    @Test
    void dataSourceTest() throws ServletException, IOException, FrameworkException {
        Row row = new Row();
        row.setString("Numero", "1,2,3");

        multipleAutoComplete.copyFromDataSource(row);

        assertEquals("1,2,3", multipleAutoComplete.getData());
        assertEquals("Valore A,Valore B,Valore C", multipleAutoComplete.getDecodedText());

        assertEquals(3, multipleAutoComplete.getValue().size());
        assertEquals(BigDecimal.valueOf(1), multipleAutoComplete.getValue().get(0));


        Row row2 = new Row();
        multipleAutoComplete.copyToDataSource(row2);
        assertEquals("1,2,3", row2.getString("Numero"));
    }
}
