package it.eg.sloth.webdesktop.tag;

import javax.servlet.http.HttpServletRequest;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.framework.view.AbstractTag;
import it.eg.sloth.webdesktop.common.SessionManager;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;

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
public abstract class WebDesktopTag<F extends Form> extends AbstractTag {

    private static final long serialVersionUID = 1L;

    public WebDesktopDto getWebDesktopDto() {
        return SessionManager.getWebDesktopDto(pageContext.getSession());
    }

    public User getUser() {
        return getWebDesktopDto().getUser();
    }

    /**
     * Restituisce la form associata alla pagina
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    protected F getForm() {
        return (F) getWebDesktopDto().getForm();
    }

    /**
     * Restituisce la modalità di visualizzazione
     *
     * @return
     */
    protected ViewModality getViewModality() {
        return getForm().getPageInfo().getViewModality();
    }

    protected String getUrl() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String url = (String) request.getAttribute("javax.servlet.forward.request_uri");
        String queryString = (String) request.getAttribute("javax.servlet.forward.query_string");

        return url + (BaseFunction.isBlank(queryString) ? "" : "?" + queryString);
    }
}
