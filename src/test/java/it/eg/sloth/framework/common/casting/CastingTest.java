package it.eg.sloth.framework.common.casting;

import it.eg.sloth.framework.common.base.StringUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CastingTest {

    @Test
    public void getHtmlTest() {
        assertEquals(StringUtil.EMPTY, Casting.getHtml(null));
        assertEquals(StringUtil.EMPTY, Casting.getHtml(""));

        assertEquals("&nbsp; ", Casting.getHtml("  "));
        assertEquals("&euro;", Casting.getHtml(String.valueOf('€')));
        assertEquals("&agrave;", Casting.getHtml(String.valueOf('à')));
        assertEquals("&ograve;", Casting.getHtml(String.valueOf('ò')));
        assertEquals("&egrave;", Casting.getHtml(String.valueOf('è')));
    }

    @Test
    public void getJsTest() {
        assertEquals(StringUtil.EMPTY, Casting.getJs(null));
        assertEquals(StringUtil.EMPTY, Casting.getJs(""));

        assertEquals("1\\\"2", Casting.getJs("1\"2"));
        assertEquals("1\\n2", Casting.getJs("1\n2"));
        assertEquals("1\\t2", Casting.getJs("1\t2"));
    }

}
