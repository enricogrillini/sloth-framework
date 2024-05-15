package it.eg.sloth.form;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.form.fields.field.impl.MultipleAutoComplete;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.pageinfo.ViewModality;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

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
class MultipleAutoCompleteTest {

    private MessageList messageList;
    MultipleAutoComplete<BigDecimal> field;
    MultipleAutoComplete<String> fieldTopic;


    @BeforeEach
    public void init() {
        messageList = new MessageList();

        field = new MultipleAutoComplete<>("Numero", "Numero", DataTypes.INTEGER);
        field.setDecodeMap(TestFactory.getBaseDecodeMap());

        fieldTopic = new MultipleAutoComplete<>("Topic", "Topic", DataTypes.STRING);
        fieldTopic.setDecodeMap(TestFactory.getTopicDecodeMap());
    }


    @Test
    void builder_base() throws FrameworkException {
        assertEquals("numero", field.getName());
        assertEquals("Numero", field.getDescription());
        assertEquals("numero", field.getAlias());
        assertFalse(field.isRequired());
        assertFalse(field.isReadOnly());
        assertFalse(field.isHidden());
        assertEquals(ViewModality.AUTO, field.getViewModality());
    }

    @Test
    void builder_topic() throws FrameworkException {
        assertEquals("topic", fieldTopic.getName());
        assertEquals("Topic", fieldTopic.getDescription());
        assertEquals("topic", fieldTopic.getAlias());
        assertFalse(fieldTopic.isRequired());
        assertFalse(fieldTopic.isReadOnly());
        assertFalse(fieldTopic.isHidden());
        assertEquals(ViewModality.AUTO, fieldTopic.getViewModality());
    }

    @Test
    void builder_withNotValidField() throws FrameworkException {
        field.setValue(Arrays.asList(BigDecimal.valueOf(1L), BigDecimal.valueOf(4L)));

        assertEquals("Valore A|Valore D", field.getDecodedText());
        assertEquals("Valore D", field.getInvalidDecodedText());
    }


    @Test
    void validate_request() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A|Valore B|Valore C"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);

        field.post(webRequest);

        assertEquals("1|2|3", field.getData());
        assertEquals("Valore A, Valore B, Valore C", field.getText());
        assertEquals("Valore A|Valore B|Valore C", field.getDecodedText());

        MessageList messageList = new MessageList();
        field.validate(messageList);
        assertTrue(messageList.isEmpty());


        field.addValue(BigDecimal.valueOf(4));
        assertEquals("1|2|3|4", field.getData());
    }


    @Test
    void validate_request_KO() throws ServletException, IOException, FrameworkException {
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Numero", new String[]{"Valore A,Valore B,Valore X"});

        WebRequest webRequest = TestFactory.getMockedWebRequest(map);
        field.post(webRequest);

        assertEquals(StringUtil.EMPTY, field.getData());
        assertEquals(StringUtil.EMPTY, field.getText());
        assertEquals("Valore A,Valore B,Valore X", field.getDecodedText());

        MessageList messageList = new MessageList();
        field.validate(messageList);
        assertEquals("Il campo Numero contiene valori non validi", messageList.getMessagesDescription());
        assertFalse(messageList.isEmpty());
    }


    @Test
    void validate_dataSource() throws ServletException, IOException, FrameworkException {
        Row row = new Row();
        row.setString("Numero", "1|2|3");

        field.copyFromDataSource(row);

        assertEquals("1|2|3", field.getData());
        assertEquals("Valore A|Valore B|Valore C", field.getDecodedText());

        assertEquals(3, field.getValue().size());
        assertEquals(BigDecimal.valueOf(1), field.getValue().get(0));


        Row row2 = new Row();
        field.copyToDataSource(row2);
        assertEquals("1|2|3", row2.getString("Numero"));
    }

    @Test
    void validate_request_topic() throws ServletException, IOException, FrameworkException {
        MessageList messageList = new MessageList();
        HashMap<String, String[]> map = new HashMap<>();
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C"});

        fieldTopic.post(TestFactory.getMockedWebRequest(map));
        assertEquals("Topic A|Topic B|Topic C", fieldTopic.getData());
        assertEquals("Topic A, Topic B, Topic C", fieldTopic.getText());
        assertEquals("Topic A|Topic B|Topic C", fieldTopic.getDecodedText());

        fieldTopic.validate(messageList);
        assertTrue(messageList.isEmpty());

        // New Topic - Free input: false
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C|New Topic"});

        fieldTopic.post(TestFactory.getMockedWebRequest(map));
        assertEquals("", fieldTopic.getData());
        assertEquals("", fieldTopic.getText());
        assertEquals("Topic A|Topic B|Topic C|New Topic", fieldTopic.getDecodedText());

        fieldTopic.validate(messageList);
        assertFalse(messageList.isEmpty());

        // New Topic - Free input: true
        fieldTopic.setFreeInput(true);
        map.put("Topic", new String[]{"Topic A|Topic B|Topic C|New Topic"});

        fieldTopic.post(TestFactory.getMockedWebRequest(map));
        assertEquals("Topic A|Topic B|Topic C|New Topic", fieldTopic.getData());
        assertEquals("Topic A, Topic B, Topic C, New Topic", fieldTopic.getText());
        assertEquals("Topic A|Topic B|Topic C|New Topic", fieldTopic.getDecodedText());

        fieldTopic.validate(messageList);
        assertFalse(messageList.isEmpty());
    }

}
