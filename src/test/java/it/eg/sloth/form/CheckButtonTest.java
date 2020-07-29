package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.CheckButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class CheckButtonTest {

    CheckButtons<List<String>, String> checkButtons;
    HttpServletRequest request;

    @Before
    public void init() {
        checkButtons = CheckButtons.<List<String>, String>builder()
                .name("Name")
                .description("description")
                .values("AA,BB,CC")
                .dataType(DataTypes.STRING)
                .build();

        // Mock HttpServletRequest
        HashMap<String, String[]> map = new HashMap<>();
        map.put("name", new String[]{"AA", "BB"});

        request = Mockito.mock(HttpServletRequest.class);
        when(request.getParameterMap()).thenReturn(map);
    }

    @Test
    public void checkButtonBuilderTest() {
        assertEquals("name", checkButtons.getName());
        assertEquals("description", checkButtons.getDescription());
        assertEquals("name", checkButtons.getAlias());
        assertFalse(checkButtons.isRequired());
        assertFalse(checkButtons.isReadOnly());
        assertFalse(checkButtons.isHidden());
        assertEquals(ViewModality.VIEW_AUTO, checkButtons.getViewModality());
    }

    @Test
    public void checkButtonWebRequestTest() throws IOException, ServletException, FrameworkException {
        WebRequest webRequest = new WebRequest(request);
        checkButtons.post(webRequest);

        assertTrue(checkButtons.isChecked("AA"));
        assertTrue(checkButtons.isChecked("BB"));
        assertFalse(checkButtons.isChecked("CC"));
    }
}
