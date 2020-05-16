package it.eg.sloth.framework.common.util.html;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.html.HtmlColor;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

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
 *
 */
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
