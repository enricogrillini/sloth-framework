package it.eg.sloth.webdesktop.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
public class SessionManager {

    public static final String WEB_DESKTOP_DTO = "WEB_DESKTOP_DTO";

    private SessionManager() {
        // NOP
    }

    /**
     * Ritorna il Dto con le informazioni contenute in sessione
     *
     * @param request
     * @return
     */
    public static final WebDesktopDto getWebDesktopDto(HttpServletRequest request) {
        return getWebDesktopDto(request.getSession());
    }

    /**
     * Ritorna il Dto con le informazioni contenute in sessione
     *
     * @param session
     * @return
     */
    public static final WebDesktopDto getWebDesktopDto(HttpSession session) {
        WebDesktopDto webDesktopDto = (WebDesktopDto) session.getAttribute(WEB_DESKTOP_DTO);

        if (webDesktopDto == null) {
            webDesktopDto = new WebDesktopDto();
            session.setAttribute(WEB_DESKTOP_DTO, webDesktopDto);
        }

        return webDesktopDto;
    }
}
