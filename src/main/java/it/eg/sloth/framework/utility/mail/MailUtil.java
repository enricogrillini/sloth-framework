package it.eg.sloth.framework.utility.mail;

import java.nio.charset.StandardCharsets;

import org.apache.commons.mail.HtmlEmail;

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
