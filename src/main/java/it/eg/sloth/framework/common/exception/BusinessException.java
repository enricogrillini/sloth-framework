package it.eg.sloth.framework.common.exception;

import it.eg.sloth.framework.common.localization.Localization;

import java.util.ResourceBundle;

public class BusinessException extends Exception {

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
