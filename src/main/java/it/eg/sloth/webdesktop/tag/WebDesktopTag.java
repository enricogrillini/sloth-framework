package it.eg.sloth.webdesktop.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.common.SessionManager;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

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
@Slf4j
public abstract class WebDesktopTag<F extends Form> extends TagSupport {

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

    /**
     * Metodo richiamato da doStartTag
     *
     * @return
     */
    protected abstract int startTag() throws Throwable;

    /**
     * Metodo richiamato da doStartTag
     *
     * @return
     */
    protected abstract void endTag() throws Throwable;

    /**
     * Ritorna l'attributo specificato prelevandolo dalla session
     *
     * @param name
     * @return
     */
    protected Object getObjectFromSession(String name) {
        return pageContext.getSession().getAttribute(name);
    }

    /**
     * Stampa il testo passato
     *
     * @param testo
     */
    protected void write(String testo) throws IOException {
        pageContext.getOut().print(testo);
    }

    /**
     * Stampa una riga vuota
     */
    protected void writeln() throws IOException {
        pageContext.getOut().println();
    }

    /**
     * Stampa il testo passato
     *
     * @param testo
     */
    protected void writeln(String testo) throws IOException {
        pageContext.getOut().println(testo);
    }

    @Override
    public int doStartTag() {
        try {
            return startTag();

        } catch (Throwable e) {
            log.error("doStartTag {}", e.getMessage(), e);
            try {
                writeln("Errore nel metodo " + getClass().getName() + ".startTag: " + e.toString());
            } catch (IOException ex) {
                log.error("doStartTag", ex);
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            endTag();
        } catch (Throwable e) {
            log.error("doEndTag\n", e);
            try {
                writeln("<!-- Errore nel metodo " + getClass().getName() + ".endTag: " + e.toString() + " -->");
            } catch (IOException ex) {
                log.error("doStartTag", ex);
            }
        }

        return super.doEndTag();

    }
}
