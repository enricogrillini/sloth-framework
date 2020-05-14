package it.eg.sloth.common.util.html;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Test;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.html.HtmlColor;

public class HtmlColorUtilTest {

  @Test
  public void rgbFromHexTest() throws ParseException {
    assertEquals("rgb(78, 115, 223)", HtmlColor.rgbFromHex("#4e73df"));
    assertEquals("rgb(78, 115, 223)", HtmlColor.rgbFromHex("#4E73DF"));
    assertEquals("rgb(255, 255, 255)", HtmlColor.rgbFromHex("#FFFFFF"));
    assertEquals(StringUtil.EMPTY, HtmlColor.rgbFromHex(null));
  }

  @Test
  public void rgbaFromHexTest() throws ParseException {
    assertEquals("rgba(78, 115, 223, 0.05)", HtmlColor.rgbaFromHex("#4e73df", 0.05));
    assertEquals("rgba(78, 115, 223, 0.05)", HtmlColor.rgbaFromHex("#4E73DF", 0.05));
    assertEquals("rgba(255, 255, 255, 0.05)", HtmlColor.rgbaFromHex("#FFFFFF", 0.05));
    assertEquals(StringUtil.EMPTY, HtmlColor.rgbaFromHex(null, 0.05));
  }

}
