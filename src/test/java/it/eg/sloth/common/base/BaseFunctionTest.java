package it.eg.sloth.common.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import it.eg.sloth.framework.common.base.BaseFunction;


public class BaseFunctionTest {

    @Test
    public void isBlankTest() {
        assertTrue(BaseFunction.isBlank(null));
        assertTrue(BaseFunction.isBlank(""));
        assertTrue(BaseFunction.isBlank("   "));
        assertFalse(BaseFunction.isBlank("pippo"));
    }

    @Test
    public void isNullTest() {
        assertTrue(BaseFunction.isNull(null));
        assertTrue(BaseFunction.isNull(""));
        assertFalse(BaseFunction.isNull(new BigDecimal(0)));
    }
}
