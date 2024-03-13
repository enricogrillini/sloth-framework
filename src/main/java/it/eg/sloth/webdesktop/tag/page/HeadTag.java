package it.eg.sloth.webdesktop.tag.page;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;
import it.eg.sloth.webdesktop.tag.page.writer.PageWriter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
 * <p>
 *
 * @author Enrico Grillini
 */
public class HeadTag extends WebDesktopTag<Form> {

    public static final String APPLICATION_TITLE = BaseFunction.isBlank(ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_APP_TITLE)) ? StringUtil.EMPTY : ConfigSingleton.getInstance().getString(ConfigSingleton.FRAMEWORK_APP_TITLE) + " | ";

    @Override
    protected int startTag() throws IOException {
        pageContext.getResponse().setContentType("text/html; charset=UTF-8");
        pageContext.getResponse().setCharacterEncoding(StandardCharsets.UTF_8.name());

        write(PageWriter.openHead(APPLICATION_TITLE + Casting.getHtml(getWebDesktopDto().getForm().getPageInfo().getTitle()), getCrsfToken()));

        return EVAL_BODY_INCLUDE;
    }

    @Override
    protected void endTag() throws IOException {
        write(PageWriter.closeHead());
    }

}
