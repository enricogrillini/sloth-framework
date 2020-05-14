package it.eg.sloth.framework.common.exception;

public enum BusinessExceptionType {

    FORMAT_ERROR("exception.formatError"),
    PARSE_ERROR("exception.parseError"),
    ROLLUP_CALCULATE_ERROR("exception.rollupCalculateError");

    String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    BusinessExceptionType(String propertyName) {
        this.propertyName = propertyName;
    }

}
