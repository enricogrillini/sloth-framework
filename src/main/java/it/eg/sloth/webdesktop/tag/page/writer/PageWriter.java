package it.eg.sloth.webdesktop.tag.page.writer;

import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.HtmlWriter;

import java.text.MessageFormat;

public class PageWriter extends HtmlWriter {

    public static final String OPEN_HEAD = ResourceUtil.resourceAsString("snippet/page/head-open.html");

    public static final String CLOSE_HEAD =  ResourceUtil.resourceAsString("snippet/page/head-close.html");

    public static final String COPYRIGHT_INFO = ResourceUtil.resourceAsString("snippet/page/copyright.html");

    private PageWriter() {
        // NOP
    }

    public static final String openHead(String title) {
        return MessageFormat.format(OPEN_HEAD, title);
    }

    public static final String closeHead() {
        return CLOSE_HEAD;
    }


    public static final String copyright() {
        return COPYRIGHT_INFO;

    }

}
