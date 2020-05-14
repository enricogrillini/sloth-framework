package it.eg.sloth.common.base;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import it.eg.sloth.framework.common.base.StringUtil;

public class StringUtilTest {

  @Test
  public void replaceTest() {
    assertEquals(StringUtil.EMPTY, StringUtil.replace(null, "\"", "\\\""));
    assertEquals(StringUtil.EMPTY, StringUtil.replace("", "\"", "\\\""));
    assertEquals(">>\\\"<<", StringUtil.replace(">>\"<<", "\"", "\\\""));
  }
}
