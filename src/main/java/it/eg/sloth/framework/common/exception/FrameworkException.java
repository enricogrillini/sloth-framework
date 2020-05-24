package it.eg.sloth.framework.common.exception;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.localization.Localization;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

@Getter
@Setter
public class FrameworkException extends Exception {

    final ExceptionCode exceptionCode;

    /**
     * Definisce il messaggio d'errore sulla base del exceptionCode
     *
     * @param exceptionCode
     * @return
     */
    private static String decodeMessage(ExceptionCode exceptionCode, String additionalMessage) {
        ResourceBundle bundle = ResourceBundle.getBundle(Localization.EXCEPTION_BUNDLE);
        if (BaseFunction.isBlank(additionalMessage)) {
            return bundle.getString(exceptionCode.getPropertyName());
        } else {
            return bundle.getString(exceptionCode.getPropertyName()) + " - " + additionalMessage;
        }
    }

    public FrameworkException(ExceptionCode exceptionCode) {
        this(exceptionCode, null, null);
    }


    public FrameworkException(ExceptionCode exceptionCode, Throwable throwable) {
        this(exceptionCode, null, throwable);
    }

    public FrameworkException(ExceptionCode exceptionCode, String additionalMessage) {
        this(exceptionCode, additionalMessage, null);
    }

    public FrameworkException(ExceptionCode exceptionCode, String additionalMessage, Throwable throwable) {
        super(decodeMessage(exceptionCode, additionalMessage), throwable);
        this.exceptionCode = exceptionCode;
    }

    public ExceptionType getExceptionType() {
        return getExceptionCode().getExceptionType();
    }

}
