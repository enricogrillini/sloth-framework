package it.eg.sloth.framework.common.exception;

public enum ExceptionCode {

    // Business Exception
    FORMAT_ERROR("business.exception.formatError", ExceptionType.BUSINESS),
    PARSE_ERROR("business.exception.parseError", ExceptionType.BUSINESS),
    ROLLUP_CALCULATE_ERROR("business.exception.rollupCalculateError", ExceptionType.BUSINESS),
    INVALID_CONTROL("business.exception.invalidControl", ExceptionType.BUSINESS),

    // System Exception
    DATA_SOURCE_NOT_FOUND("system.exception.dataSourceNotFound", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_REMOVE("system.exception.transactionRemove", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_UNDO("system.exception.transactionundo", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_SAVE("system.exception.transactionSave", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_POST("system.exception.transactionPost", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_UNPOST("system.exception.transactionUnpost", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_COMMIT("system.exception.transactionCommit", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_ROLLBACK("system.exception.transactionRollback", ExceptionType.SYSTEM);

    String propertyName;
    ExceptionType exceptionType;

    public String getPropertyName() {
        return propertyName;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    ExceptionCode(String propertyName, ExceptionType exceptionType) {
        this.propertyName = propertyName;
        this.exceptionType = exceptionType;
    }

}
