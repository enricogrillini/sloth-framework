package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
public class PasswordUtil {

    private PasswordUtil() {
        // NOP
    }

    /**
     * Verifica se la stringa passata corrisponde ad una passwrod valida:
     * - tra 8 e 16 caratteri
     * - ammessi solo lettere e numeri
     *
     * @param secret
     * @return
     */
    public static boolean isPasswordValid(String secret) {
        if (secret == null) {
            return false;
        }

        if (secret.length() < 8) {
            return false;
        }

        if (secret.length() > 16) {
            return false;
        }

        secret = secret.toLowerCase();
        for (int i = 0; i < secret.length(); i++) {
            char c = secret.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Genera l'hash di un secret
     *
     * @param secret
     * @return
     */
    public static String hash(String secret) throws FrameworkException {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(secret.getBytes());
            byte[] digest = md.digest();

            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            throw new FrameworkException(ExceptionCode.GENERIC_SYSTEM_ERROR, "Algoritmo errato", e);
        }
    }

    /**
     * Maschera una segreto
     *
     * @param secret
     * @return
     */
    public static String mask(String secret) {
        if (StringUtils.isEmpty(secret)) {
            return "[Empty]";
        } else if (secret.length() > 10) {
            return StringUtils.rightPad(secret.substring(0, 3), secret.length() - 3, "*") + secret.substring(secret.length() - 3);
        } else if (secret.length() >= 8) {
            return StringUtils.rightPad(secret.substring(0, 2), secret.length() - 2, "*") + secret.substring(secret.length() - 2);
        } else {
            return "[Short] - " + secret.length();
        }
    }

}
