package it.eg.sloth.webdesktop.tag;

import javax.servlet.http.HttpServletRequest;

import it.eg.sloth.form.Form;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.pageinfo.ViewModality;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.framework.view.AbstractTag;
import it.eg.sloth.webdesktop.common.SessionManager;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;

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
