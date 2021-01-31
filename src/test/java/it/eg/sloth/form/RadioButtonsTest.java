package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.RadioButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.webdesktop.api.model.BffFieldsProva;
import it.eg.sloth.webdesktop.api.request.BffFields;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RadioButtonsTest {

    RadioButtons<String> radioButtons1;

    @Before
    public void init() {
        radioButtons1 = RadioButtons.<String>builder()
                .name("testo")
                .description("description")
                .dataType(DataTypes.STRING)
                .build();
    }

    @Test
    public void radioButtonsBuilderTest() {
        assertEquals("testo", radioButtons1.getName());
        assertEquals("description", radioButtons1.getDescription());
        assertEquals("testo", radioButtons1.getAlias());
        assertFalse(radioButtons1.isRequired());
        assertFalse(radioButtons1.isReadOnly());
        assertFalse(radioButtons1.isHidden());
        assertEquals(ViewModality.VIEW_AUTO, radioButtons1.getViewModality());
    }

    @Test
    public void radioButtonsWebRequestTest() throws IOException, ServletException, FrameworkException {
        // Mock HttpServletRequest
        HashMap<String, String[]> map = new HashMap<>();
        map.put("testo", new String[]{"AA", "BB"});

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getParameterMap()).thenReturn(map);

        WebRequest webRequest = new WebRequest(request);

        // Test
        radioButtons1.post(webRequest);
        assertNull(radioButtons1.check());

        radioButtons1.setRequired(true);
        radioButtons1.post(webRequest);
        assertNull(radioButtons1.check());

        radioButtons1.setName("testo2");
        radioButtons1.post(webRequest);
        assertNotNull(radioButtons1.check());
    }

    @Test
    public void radioButtonsBffTest() throws IOException, ServletException, FrameworkException {
        BffFieldsProva bffFields = new BffFieldsProva();
        bffFields.setTesto("Aa");

        radioButtons1.post(bffFields);
        assertNull(radioButtons1.check());

        radioButtons1.setRequired(true);
        radioButtons1.post(bffFields);
        assertNull(radioButtons1.check());

        radioButtons1.setName("testo2");
        radioButtons1.post(bffFields);
        assertNotNull(radioButtons1.check());
    }
}
