package it.eg.sloth.framework.utility.mail.asyncmail;

import it.eg.sloth.framework.common.base.TimeStampUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.HtmlEmail;

import java.sql.Timestamp;

@Getter
@Setter
public class MailToSend {
    private HtmlEmail htmlEmail;
    private Exception sendError;
    private Timestamp submitDate;
    private Timestamp lastTry;

    public MailToSend(HtmlEmail htmlEmail) {
        this.htmlEmail = htmlEmail;
        this.submitDate = TimeStampUtil.sysdate();
        this.sendError = null;
        this.lastTry = null;
    }
}
