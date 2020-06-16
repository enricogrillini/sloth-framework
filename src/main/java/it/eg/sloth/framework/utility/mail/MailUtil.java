package it.eg.sloth.framework.utility.mail;

import java.nio.charset.StandardCharsets;

import org.apache.commons.mail.HtmlEmail;

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
public class MailUtil {

    private MailUtil() {
        // NOP
    }

    /**
     * Crea inizializzandola una HtmlEmail
     *
     * @param mailServerDto
     * @return
     */
    public static HtmlEmail createMail(MailAccountDto mailServerDto) {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(mailServerDto.getMailServer());
        email.setSmtpPort(mailServerDto.getSmtpPort());

        if (mailServerDto.isSsl()) {
            email.setSslSmtpPort(Integer.toString(mailServerDto.getSmtpPort()));
        }

        email.setSSLOnConnect(mailServerDto.isSsl());
        email.setStartTLSEnabled(mailServerDto.isTls());

        email.setSocketConnectionTimeout(10000);
        email.setSocketTimeout(10000);

        if (mailServerDto.getMailUser() != null && mailServerDto.getMailPassword() != null) {
            email.setAuthentication(mailServerDto.getMailUser(), mailServerDto.getMailPassword());
        }
        email.setCharset(StandardCharsets.ISO_8859_1.name());

        return email;

    }

}
