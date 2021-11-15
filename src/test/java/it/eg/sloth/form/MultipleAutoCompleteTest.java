package it.eg.sloth.form;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.base.StringUtil;
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
class MultipleAutoCompleteTest {

    MultipleAutoComplete<BigDecimal> baseMultipleAutoComplete;
    MultipleAutoComplete<String> topicMultipleAutoComplete;


    @BeforeEach
    public void init() {
        baseMultipleAutoComplete = new MultipleAutoComplete<>("Numero", "Numero", DataTypes.INTEGER);
        baseMultipleAutoComplete.setDecodeMap(TestFactory.getBaseDecodeMap());

        topicMultipleAutoComplete = new MultipleAutoComplete<>("Topic", "Topic", DataTypes.STRING);
        topicMultipleAutoComplete.setDecodeMap(TestFactory.getTopicDecodeMap());
    }

    @Test
    void baseTest() throws FrameworkException {
        List<BigDecimal> values = new ArrayList<>();
        values.add(BigDecimal.valueOf(1));
        values.add(BigDecimal.valueOf(2));

        baseMultipleAutoComplete.setValue(values);
        assertEquals("1|2", baseMultipleAutoComplete.getData());
        assertEquals("Valore A, Valore B", baseMultipleAutoComplete.getText());
        assertEquals("Valore A|Valore B", baseMultipleAutoComplete.getDecodedText());

        baseMultipleAutoComplete.setValue(null);
        assertEquals(StringUtil.EMPTY, baseMultipleAutoComplete.getData());
        assertEquals(StringUtil.EMPTY, baseMultipleAutoComplete.getText());
        assertEquals(null, baseMultipleAutoComplete.getDecodedText());
    }

    @Test
    void requestOkTest() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A|Valore B|Valore C"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);

        baseMultipleAutoComplete.post(webRequest);

        assertEquals("1|2|3", baseMultipleAutoComplete.getData());
        assertEquals("Valore A, Valore B, Valore C", baseMultipleAutoComplete.getText());
        assertEquals("Valore A|Valore B|Valore C", baseMultipleAutoComplete.getDecodedText());

        MessageList messageList = new MessageList();
        baseMultipleAutoComplete.validate(messageList);
        assertTrue(messageList.isEmpty());
    }

    @Test
    void requestKoTest() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A,Valore B,Valore X"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);
        baseMultipleAutoComplete.post(webRequest);

        assertEquals(StringUtil.EMPTY, baseMultipleAutoComplete.getData());
        assertEquals(StringUtil.EMPTY, baseMultipleAutoComplete.getText());
        assertEquals("Valore A,Valore B,Valore X", baseMultipleAutoComplete.getDecodedText());

        MessageList messageList = new MessageList();
        baseMultipleAutoComplete.validate(messageList);
        assertEquals("Il campo Numero contiene valori non validi", messageList.getMessagesDescription());
        assertFalse(messageList.isEmpty());
    }

    @Test
    void dataSourceTest() throws ServletException, IOException, FrameworkException {
        Row row = new Row();
        row.setString("Numero", "1|2|3");

        baseMultipleAutoComplete.copyFromDataSource(row);

        assertEquals("1|2|3", baseMultipleAutoComplete.getData());
        assertEquals("Valore A|Valore B|Valore C", baseMultipleAutoComplete.getDecodedText());

        assertEquals(3, baseMultipleAutoComplete.getValue().size());
        assertEquals(BigDecimal.valueOf(1), baseMultipleAutoComplete.getValue().get(0));


        Row row2 = new Row();
        baseMultipleAutoComplete.copyToDataSource(row2);
        assertEquals("1|2|3", row2.getString("Numero"));
    }

    @Test
    void requestTopicTest() throws ServletException, IOException, FrameworkException {
        MessageList messageList = new MessageList();
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C"});

        topicMultipleAutoComplete.post(TestFactory.getMockedWebRequest(map));
        assertEquals("Topic A|Topic B|Topic C", topicMultipleAutoComplete.getData());
        assertEquals("Topic A, Topic B, Topic C", topicMultipleAutoComplete.getText());
        assertEquals("Topic A|Topic B|Topic C", topicMultipleAutoComplete.getDecodedText());

        topicMultipleAutoComplete.validate(messageList);
        assertTrue(messageList.isEmpty());

        // New Topic - Free input: false
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C|New Topic"});

        topicMultipleAutoComplete.post(TestFactory.getMockedWebRequest(map));
        assertEquals("", topicMultipleAutoComplete.getData());
        assertEquals("", topicMultipleAutoComplete.getText());
        assertEquals("Topic A|Topic B|Topic C|New Topic", topicMultipleAutoComplete.getDecodedText());

        topicMultipleAutoComplete.validate(messageList);
        assertFalse(messageList.isEmpty());

        // New Topic - Free input: true
        topicMultipleAutoComplete.setFreeInput(true);
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C|New Topic"});

        topicMultipleAutoComplete.post(TestFactory.getMockedWebRequest(map));
        assertEquals("Topic A|Topic B|Topic C|New Topic", topicMultipleAutoComplete.getData());
        assertEquals("Topic A, Topic B, Topic C, New Topic", topicMultipleAutoComplete.getText());
        assertEquals("Topic A|Topic B|Topic C|New Topic", topicMultipleAutoComplete.getDecodedText());

        topicMultipleAutoComplete.validate(messageList);
        assertFalse(messageList.isEmpty());

    }



}
