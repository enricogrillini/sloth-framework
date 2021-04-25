package it.eg.sloth.form;

import it.eg.sloth.form.fields.field.impl.CheckButtons;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.pageinfo.ViewModality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
class CheckButtonTest {

    CheckButtons<List<String>, String> checkButtons;
    HttpServletRequest request;

    @BeforeEach
    void init() {
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
    void checkButtonBuilderTest() {
        assertEquals("name", checkButtons.getName());
        assertEquals("description", checkButtons.getDescription());
        assertEquals("name", checkButtons.getAlias());
        assertFalse(checkButtons.isRequired());
        assertFalse(checkButtons.isReadOnly());
        assertFalse(checkButtons.isHidden());
        assertEquals(ViewModality.VIEW_AUTO, checkButtons.getViewModality());
    }

    @Test
    void checkButtonWebRequestTest() throws IOException, ServletException, FrameworkException {
        WebRequest webRequest = new WebRequest(request);
        checkButtons.post(webRequest);

        assertTrue(checkButtons.isChecked("AA"));
        assertTrue(checkButtons.isChecked("BB"));
        assertFalse(checkButtons.isChecked("CC"));
    }
}
