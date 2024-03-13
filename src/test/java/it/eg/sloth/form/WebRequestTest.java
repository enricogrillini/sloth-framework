package it.eg.sloth.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
@ExtendWith(MockitoExtension.class)
class WebRequestTest {

    @Mock
    HttpServletRequest httpServletRequest;

    @BeforeEach
    void init() throws IOException, ServletException {
        Map<String, String[]> map = new HashMap<>();
        map.put("provaKey1", new String[]{"provaValue1", "provaValuea"});
        map.put("provaKey2", new String[]{"provaValue2"});

        // Simulo una GET così non è multipart
//        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        Mockito.when(httpServletRequest.getContentType()).thenReturn("map");
        Mockito.when(httpServletRequest.getParameterMap()).thenReturn(map);
//        Mockito.when(httpServletRequest.getParts()).thenReturn(new ArrayList<>());
    }

    @Test
    void webRequestTest() throws IOException, ServletException {
        WebRequest webRequest = new WebRequest(httpServletRequest);

        assertEquals("provaValue1", webRequest.getString("provaKey1"));
        assertEquals("provaValue2", webRequest.getString("provaKey2"));
    }
}
