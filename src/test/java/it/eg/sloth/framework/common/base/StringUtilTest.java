package it.eg.sloth.framework.common.base;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import it.eg.sloth.framework.common.exception.BusinessException;

public class StringUtilTest {

    @Test
    public void bigDecimalFormatValueTest() throws BusinessException {
        List<String> words = Arrays.asList("Bob", "Alice");

        assertTrue(StringUtil.containsAllWords("Bob and Alice", words));
        assertTrue(StringUtil.containsAllWords("Alice and Bob", words));
        assertFalse(StringUtil.containsAllWords("Only Bob ", words));
        assertFalse(StringUtil.containsAllWords("", words));
    }
}
