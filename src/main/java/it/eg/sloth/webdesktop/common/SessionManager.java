package it.eg.sloth.webdesktop.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import it.eg.sloth.webdesktop.dto.WebDesktopDto;

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
