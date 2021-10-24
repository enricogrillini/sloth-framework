package it.eg.sloth.webdesktop.controller;

import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.dto.WebDesktopDto;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

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
 * Gestisce l'interfaccia di base per una pagina
 *
 * @author Enrico Grillini
 */
public interface BasePageInterface extends Controller {

    default String getFunctionName() {
        return null;
    }

    // Ritorna il web desktop Dto
    WebDesktopDto getWebDesktopDto();

    // Ritorna l'utente attualmente connesso
    User getUser();

    // Imposta l'utente attualmente connesso
    void setUser(User user);

    // Gestisce la risposta alla chiamata
    ModelAndView service() throws Exception;

}
