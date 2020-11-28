package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaswordUtilTest {

    @Test
    public void isPasswordValidTest() throws FrameworkException {
        assertTrue(PasswordUtil.isPasswordValid("ILoveJava"));
        assertTrue(PasswordUtil.isPasswordValid("LoveJava"));
        assertTrue(PasswordUtil.isPasswordValid("LoveJavaLoveJava"));
        assertTrue(PasswordUtil.isPasswordValid("ILoveJava7678324"));

        assertFalse(PasswordUtil.isPasswordValid("ILove"));
        assertFalse(PasswordUtil.isPasswordValid("LoveJavaLoveJavaLoveJava"));
        assertFalse(PasswordUtil.isPasswordValid("ILòveJava"));
        assertFalse(PasswordUtil.isPasswordValid("IL#veJava"));
    }

    @Test
    public void maskTest() {
        assertEquals("[Empty]", PasswordUtil.mask(null));
        assertEquals("123******012", PasswordUtil.mask("123456789012"));
        assertEquals("12****78", PasswordUtil.mask("12345678"));
        assertEquals("[Short] - 6", PasswordUtil.mask("123456"));
    }

    @Test
    public void hashSecretTest() throws FrameworkException {
        String hash1 = PasswordUtil.hash("ILoveJava");
        String hash2 = PasswordUtil.hash("ILoveJava");

        assertNotEquals(hash1, hash2);
        assertTrue(PasswordUtil.chekHash("ILoveJava", hash1));
        assertTrue(PasswordUtil.chekHash("ILoveJava", hash2));
    }

}
