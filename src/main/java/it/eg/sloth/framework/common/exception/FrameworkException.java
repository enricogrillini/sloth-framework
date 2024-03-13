package it.eg.sloth.framework.common.exception;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.localization.Localization;
import lombok.Getter;
import lombok.Setter;

import java.util.ResourceBundle;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
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
