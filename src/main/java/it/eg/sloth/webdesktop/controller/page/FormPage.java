package it.eg.sloth.webdesktop.controller.page;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.WebDesktopConstant;
import it.eg.sloth.webdesktop.controller.BasePage;
import it.eg.sloth.webdesktop.controller.common.FormPageInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * Fornisce una prima implementazione della navigazione di base aggiungendo il
 * concetto di Form. Rispetto al BaseController aggiunge: - la gestione della
 * Form - la gestione della WebRequest - la gestione della MessageList -
 * definisce un utente base nel caso in cui non sia gia' definito
 *
 * @param <F>
 * @author Enrico Grillini
 */
@Slf4j
public abstract class FormPage<F extends Form> extends BasePage implements FormPageInterface<F> {

    private WebRequest webRequest;
    private F form;
    private boolean newForm;

    public abstract F createForm();

    protected FormPage() {
        super();

        this.webRequest = null;
        this.form = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        super.init(req, res);
        this.webRequest = new WebRequest(getRequest());

        if (!(getClass().getSimpleName() + ".html").equals(getWebDesktopDto().getLastController())) {
            getWebDesktopDto().setLastController(getClass().getSimpleName() + ".html");
            form = createForm();
            getWebDesktopDto().setForm(form);
            this.newForm = true;
        } else {
            form = (F) getWebDesktopDto().getForm();
            this.newForm = false;
        }

        if (getUser() != null && getUser().getLocale() != null) {
            form.setLocale(getUser().getLocale());
        }
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) {
        try {
            // Inizializzo la pagina
            init(req, res);

            // Se l'utente non è presente in sessione lo creo
            if (getWebDesktopDto().getUser() == null) {
                getWebDesktopDto().setUser(new User());
            }

            if (redirectToHome()) {
                // Verifico se effettuare il redirect alla Home page
                log.info("Redirect to Home");
                return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

            } else if (!accessAllowed()) {
                // Verifico la sicurezza
                log.warn("Accesso non consentito");
                getMessageList().clear();
                getMessageList().addBaseError("Accesso alla funzionalità non consentito!");
                getMessageList().setPopup(false);
                return new ModelAndView(WebDesktopConstant.Jsp.ERROR);

            } else {
                return service();
            }

        } catch (Exception e) {
            log.error("Page error {}", getClass().getName(), e);
            return null;
        }
    }

    public WebRequest getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    public F getForm() {
        return form;
    }

    public MessageList getMessageList() {
        return getWebDesktopDto().getMessageList();
    }

    public boolean isNewForm() {
        return newForm;
    }

    public void setNewForm(boolean newForm) {
        this.newForm = newForm;
    }
}
