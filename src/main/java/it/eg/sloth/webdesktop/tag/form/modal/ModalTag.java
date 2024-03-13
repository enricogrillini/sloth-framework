package it.eg.sloth.webdesktop.tag.form.modal;

import it.eg.sloth.form.modal.Modal;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;

import java.io.IOException;
import java.text.MessageFormat;

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
 *
 * @author Enrico Grillini
 */
public class ModalTag extends BaseElementTag<Modal> {

    private static final long serialVersionUID = 1L;

    public static final String MODAL_OPEN = ResourceUtil.resourceAsString("snippet/modal/modal-open.html");
    public static final String MODAL_CLOSE = ResourceUtil.resourceAsString("snippet/modal/modal-close.html");

    public int startTag() throws IOException {
        String name = StringUtil.toJavaObjectName(getElement().getName());
        String title = Casting.getHtml(getElement().getTitle());

        writeln(MessageFormat.format(MODAL_OPEN, name, title));

        return EVAL_BODY_INCLUDE;
    }

    protected void endTag() throws IOException {
        writeln(MODAL_CLOSE);
    }

}
