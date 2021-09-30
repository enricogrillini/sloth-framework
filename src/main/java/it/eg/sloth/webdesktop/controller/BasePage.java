package it.eg.sloth.webdesktop.controller;

import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.WebDesktopConstant;
import it.eg.sloth.webdesktop.common.SessionManager;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Classe base da cui derivare tutte le classi di Controller Gestisce: - il
 * trace Applicativo - la sicurezza di base - WebDesktopDto
 *
 * @author Enrico Grillini
 */
@Slf4j
@Getter
public abstract class BasePage implements Controller {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public BasePage() {
        this.request = null;
        this.response = null;
    }

    protected void init(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        this.request = req;
        this.response = res;

        // Disabilito la cache
        this.response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * Definisce la funzionalità a cui si deve essere abilitati per accedere al
     * controllo. Se non si vogliono effttuare controlli di sicurezza ritornare
     * "null"
     *
     * @return
     */
    public abstract String getFunctionName();

    protected boolean accessAllowed() {
        if (getFunctionName() == null) {
            return true;
        } else if (getWebDesktopDto().getUser() == null) {
            return false;
        } else {
            return getWebDesktopDto().getUser().accessAllowed(getFunctionName());
        }
    }

    protected boolean redirectToHome() {
        if (getFunctionName() == null) {
            return false;
        } else if (getWebDesktopDto().getUser() == null || !getWebDesktopDto().getUser().isLogged()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Ritorna il web desktop Dto
     *
     * @return
     */
    public WebDesktopDto getWebDesktopDto() {
        return SessionManager.getWebDesktopDto(request);
    }

    /**
     * Ritorna l'utente attualmente connesso
     *
     * @return
     */
    public User getUser() {
        return getWebDesktopDto().getUser();
    }

    /**
     * Imposta l'utente attualmente connesso
     *
     * @return
     */
    public void setUser(User user) {
        getWebDesktopDto().setUser(user);
    }

    /**
     * Gestisce la risposta alla chiamata
     *
     * @return
     * @throws Exception
     */
    public abstract ModelAndView service() throws Exception;

    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) {
        try {
            // Inizializzo la pagina
            init(req, res);

            if (redirectToHome()) {
                // Verifico se effettuare il redirect alla Home page
                log.info("Redirect to Home");
                return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

            } else if (!accessAllowed()) {
                // Verifico la sicurezza
                log.warn("Accesso non consentito. Redirect to Home");
                return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

            } else {
                return service();
            }

        } catch (Exception e) {
            log.error("Page error {}", getClass().getName(), e);
            return null;
        }
    }

}
