package it.eg.sloth.webdesktop.api;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.api.model.BffResponse;
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
public class BffApi {

    protected boolean checkAccess(BffResponse bffResponse, WebDesktopDto webDesktopDto) {
        if (webDesktopDto.getUser() == null || !webDesktopDto.getUser().isLogged()) {
            bffResponse.setSessionExpired(true);
            bffResponse.getMessageList().addBaseError("Sessione scaduta");

            return false;
        }

        return true;
    }

    protected <F extends Form> boolean checkAccess(BffResponse bffResponse, WebDesktopDto webDesktopDto, Class<F> myClass) {
        if (!checkAccess(bffResponse, webDesktopDto)) {
            return false;
        }

        if (myClass != null && !myClass.isInstance(webDesktopDto.getForm())) {
            bffResponse.setWrongPage(true);
            bffResponse.getMessageList().addBaseError("Pagina errata");

            return false;
        }

        return true;
    }

}
