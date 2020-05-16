package it.eg.sloth.framework.common.exception;

import it.eg.sloth.framework.common.localization.Localization;

import java.util.ResourceBundle;

public class BusinessException extends Exception {

    public static final BusinessException FORMAT_ERROR_EXCEPTION = new BusinessException(BusinessExceptionType.FORMAT_ERROR);
    public static final BusinessException PARSE_ERROR_EXCEPTION = new BusinessException(BusinessExceptionType.PARSE_ERROR);
    public static final BusinessException ROLLUP_CALCULATE_ERROR_EXCEPTION = new BusinessException(BusinessExceptionType.ROLLUP_CALCULATE_ERROR);

    private static String decodeMessage(BusinessExceptionType businessExceptionType) {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.EXCEPTION_BUNDLE);
        return bundle.getString(businessExceptionType.getPropertyName());
    }

    public BusinessException(BusinessExceptionType businessExceptionType) {
        super(decodeMessage(businessExceptionType));
    }

    public BusinessException(BusinessExceptionType businessExceptionType, Throwable t) {
        super(decodeMessage(businessExceptionType), t);
    }

}
