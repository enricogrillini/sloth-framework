package it.eg.sloth.framework.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FrameworkExcpetionTest {

    @Test
    void frameworkExcpetionTest() {
        FrameworkException frameworkExcpetion = new FrameworkException(ExceptionCode.GENERIC_BUSINESS_ERROR, "prova");

        assertEquals("Errore generico - prova", frameworkExcpetion.getMessage());
    }
}
