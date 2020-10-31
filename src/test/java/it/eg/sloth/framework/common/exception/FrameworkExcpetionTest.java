package it.eg.sloth.framework.common.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrameworkExcpetionTest {

    @Test
    public void frameworkExcpetionTest() {
        FrameworkException frameworkExcpetion = new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, "prova");

        assertEquals("Errore generico - prova", frameworkExcpetion.getMessage());
    }
}
