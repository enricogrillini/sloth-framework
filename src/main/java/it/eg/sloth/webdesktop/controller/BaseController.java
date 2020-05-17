package it.eg.sloth.webdesktop.controller;

import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.common.SessionManager;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;
import org.apache.commons.fileupload.FileUploadException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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
 * Classe base da cui derivare la logica delle pagine:
 * - il trace Applicativo
 * - la sicurezza di base
 * - WebDesktopDto
 *
 * @author Enrico Grillini
 */
public abstract class BaseController {

    private HttpServletRequest request;
    private HttpServletResponse response;

    public BaseController() {
        this.request = null;
        this.response = null;
    }

    /**
     * Definisce la funzionalità a cui si deve essere abilitati per accedere al
     * controllo. Se non si vogliono effttuare controlli di sicurezza ritornare
     * "null"
     *
     * @return
     */
    public abstract String getFunctionName();

    protected void init(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, FileUploadException {
        this.request = req;
        this.response = res;

        // Disabilito la cache
        this.response.setHeader("Cache-Control", "no-cache");
    }

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
     * Ritorna la request
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * Ritorna la response
     *
     * @return
     */
    public HttpServletResponse getResponse() {
        return response;
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
}
