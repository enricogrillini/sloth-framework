package it.eg.sloth.webdesktop.tag.form.group;

import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.form.group.writer.GroupWriter;

/**
 * @author Enrico Grillini
 */
public class RowTag extends WebDesktopTag {

    static final long serialVersionUID = 1L;

    public int startTag() throws Throwable {
        write(GroupWriter.openRow());
        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws Throwable {
        write(GroupWriter.closeRow());
    }
}
