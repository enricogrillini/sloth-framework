package it.eg.sloth.framework.common.exception;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
public enum ExceptionCode {

    // Business Exception
    GENERIC_BUSINESS_ERROR("business.exception.genericError", ExceptionType.BUSINESS),
    FORMAT_ERROR("business.exception.formatError", ExceptionType.BUSINESS),
    PARSE_ERROR("business.exception.parseError", ExceptionType.BUSINESS),
    ROLLUP_CALCULATE_ERROR("business.exception.rollupCalculateError", ExceptionType.BUSINESS),
    INVALID_CONTROL("business.exception.invalidControl", ExceptionType.BUSINESS),
    INVALID_GET("business.exception.invalidGet", ExceptionType.BUSINESS),
    INVALID_SET("business.exception.invalidSet", ExceptionType.BUSINESS),

    // System Exception
    GENERIC_SYSTEM_ERROR("system.exception.genericError", ExceptionType.SYSTEM),
    DATA_SOURCE_NOT_FOUND("system.exception.dataSourceNotFound", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_REMOVE("system.exception.transactionRemove", ExceptionType.SYSTEM),
    TRANSACTION_EXCEPTION_UNDO("system.exception.transactionUndo", ExceptionType.SYSTEM),
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
