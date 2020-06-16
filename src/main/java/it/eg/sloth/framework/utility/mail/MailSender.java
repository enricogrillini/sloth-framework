package it.eg.sloth.framework.utility.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.utility.mail.mailcomposer.MailComposer;

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
 * @deprecated
 */
public class MailSender extends FrameComponent {

    private HtmlEmail email;

    public MailSender(String server, int port) {
        this(server, port, false, false, null, null);
    }

    public MailSender(String server, int port, boolean ssl, boolean tls, String user, String password) {
        email = new HtmlEmail();
        email.setHostName(server);
        email.setSmtpPort(port);
        email.setSSLOnConnect(ssl);
        email.setStartTLSEnabled(tls);

        if (user != null && password != null) {
            email.setAuthentication(user, password);
        }
        email.setCharset(StandardCharsets.ISO_8859_1.name());
    }

    public HtmlEmail getEmail() {
        return email;
    }

    public void addTo(String[] email) throws EmailException {
        for (int i = 0; i < email.length; i++)
            this.email.addTo(email[i].trim());
    }

    public void addTo(String email, String name) throws EmailException {
        this.email.addTo(email, name);
    }

    public void addCc(String email, String name) throws EmailException {
        this.email.addCc(email, name);
    }

    public void addBcc(String email, String name) throws EmailException {
        this.email.addBcc(email, name);
    }

    public void setFrom(String email, String name) throws EmailException {
        this.email.setFrom(email, name);
    }

    public void setSubject(String subject) {
        email.setSubject(subject);
    }

    public void setMessage(String message) throws EmailException {
        email.setMsg(message);
    }

    public void setHtmlMessage(String message) throws EmailException {
        email.setHtmlMsg(message);
    }

    public void setHtmlMessage(MailComposer mailComposer) throws EmailException, IOException {
        mailComposer.writeMessage(email);
    }

    public void addAttachment(InputStream attachment, String attachmentContentType, String attachmentName, String attachmentDescription) throws IOException, EmailException {
        DataSource dataSource = new ByteArrayDataSource(attachment, attachmentContentType);
        email.attach(dataSource, attachmentName, attachmentDescription, EmailAttachment.ATTACHMENT);
    }

    public void addAttachment(byte[] attachment, String attachmentContentType, String attachmentName, String attachmentDescription) throws IOException, EmailException {
        addAttachment(new ByteArrayInputStream(attachment), attachmentContentType, attachmentName, attachmentDescription);
    }

    public void addEmbedded(InputStream attachment, String attachmentContentType, String attachmentName) throws IOException, EmailException {
        DataSource dataSource = new ByteArrayDataSource(attachment, attachmentContentType);
        email.embed(dataSource, attachmentName);
    }

    public void addEmbedded(byte[] attachment, String attachmentContentType, String attachmentName) throws IOException, EmailException {
        addEmbedded(new ByteArrayInputStream(attachment), attachmentContentType, attachmentName);
    }

    public void send() throws EmailException {
        email.send();
    }

}
