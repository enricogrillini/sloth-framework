package it.eg.sloth.webdesktop.tag.page.writer;

import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;
import org.springframework.security.web.csrf.CsrfToken;

import java.text.MessageFormat;

public class PageWriter extends HtmlWriter {

    public static final String OPEN_HEAD = ResourceUtil.resourceAsString("snippet/page/head-open.html");
    public static final String CLOSE_HEAD = ResourceUtil.resourceAsString("snippet/page/head-close.html");
    public static final String COPYRIGHT_INFO = ResourceUtil.resourceAsString("snippet/page/copyright.html");

    public static final String CSRF_META_TOKEN = ResourceUtil.resourceAsString("snippet/page/csrf-meta-token.html");
    public static final String CSRF_INPUT_TOKEN = ResourceUtil.resourceAsString("snippet/page/csrf-input-token.html");

    public static final String NAVIGATION_SEQUENCE = ResourceUtil.resourceAsString("snippet/page/navigation-sequence.html");

    private PageWriter() {
        // NOP
    }

    public static final String openHead(String title, CsrfToken csrfToken) {
        StringBuilder builder = new StringBuilder();
        builder.append(MessageFormat.format(OPEN_HEAD, title));

        if (csrfToken != null) {
            builder.append(MessageFormat.format(CSRF_META_TOKEN, csrfToken.getHeaderName(), csrfToken.getToken()));
        }

        return builder.toString();
    }

    public static final String closeHead() {
        return CLOSE_HEAD;
    }

    public static final String copyright() {
        return COPYRIGHT_INFO;
    }

    public static final String getCsrfInputToken(CsrfToken token) {
        if (token != null) {
            return MessageFormat.format(CSRF_INPUT_TOKEN, token.getParameterName(), token.getToken());
        } else {
            return StringUtil.EMPTY;
        }
    }

    public static final String getNavigationSequence(long navigationSequence) {
        return MessageFormat.format(NAVIGATION_SEQUENCE, navigationSequence);
    }

}
