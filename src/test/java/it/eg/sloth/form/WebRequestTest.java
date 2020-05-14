package it.eg.sloth.form;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WebRequestTest {

    @Mock
    HttpServletRequest httpServletRequest;

    @Before
    public void init() {
        Map<String, String[]> map = new HashMap<>();
        map.put("provaKey1", new String[]{"provaValue1", "provaValuea"});
        map.put("provaKey2", new String[]{"provaValue2"});

        // Simulo una GET così non è multipart
        Mockito.when(httpServletRequest.getMethod()).thenReturn("GET");
        Mockito.when(httpServletRequest.getParameterMap()).thenReturn(map);
    }

    @Test
    public void webRequestTest() throws UnsupportedEncodingException, FileUploadException {
        WebRequest webRequest = new WebRequest(httpServletRequest);

        assertEquals("provaValue1", webRequest.getString("provaKey1"));
        assertEquals("provaValue2", webRequest.getString("provaKey2"));
    }
}
