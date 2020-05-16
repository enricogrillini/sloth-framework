package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.BusinessException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

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
public class StringUtilTest {

  @Test
  public void replaceTest() {
    assertEquals(StringUtil.EMPTY, StringUtil.replace(null, "\"", "\\\""));
    assertEquals(StringUtil.EMPTY, StringUtil.replace("", "\"", "\\\""));
    assertEquals(">>\\\"<<", StringUtil.replace(">>\"<<", "\"", "\\\""));
  }

  @Test
  public void bigDecimalFormatValueTest() throws BusinessException {
    List<String> words = Arrays.asList("Bob", "Alice");

    assertTrue(StringUtil.containsAllWords("Bob and Alice", words));
    assertTrue(StringUtil.containsAllWords("Alice and Bob", words));
    assertFalse(StringUtil.containsAllWords("Only Bob ", words));
    assertFalse(StringUtil.containsAllWords("", words));
  }
}
