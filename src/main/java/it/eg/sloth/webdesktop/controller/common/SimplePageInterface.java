package it.eg.sloth.webdesktop.controller.common;

import it.eg.sloth.framework.utility.FileType;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

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
 * Gestisce l'interfaccia di base per una pagina
 *
 * @author Enrico Grillini
 */
public interface SimplePageInterface {

    HttpServletResponse getResponse();

    ModelAndView getModelAndView();

    void clearModelAndView();

    void setModelAndView(String fileName, FileType fileType);

    void setModelAndView(ModelAndView modelAndView);

    void setModelAndView(String modelAndView);

    void onInit() throws Exception;

    void execInit() throws Exception;

}
