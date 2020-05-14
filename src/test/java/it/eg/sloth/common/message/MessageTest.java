package it.eg.sloth.common.message;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.eg.sloth.framework.common.message.Level;

public class MessageTest {

  @Test
  public void levelTest() {
    assertFalse(Level.INFO.hasHigerSeverity(Level.SUCCESS));
    assertFalse(Level.SUCCESS.hasHigerSeverity(Level.WARN));
    assertFalse(Level.WARN.hasHigerSeverity(Level.ERROR));

    assertTrue(Level.SUCCESS.hasHigerSeverity(Level.INFO));
    assertTrue(Level.WARN.hasHigerSeverity(Level.SUCCESS));
    assertTrue(Level.ERROR.hasHigerSeverity(Level.WARN));
  }
}
