package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.form.Form;
import it.eg.sloth.webdesktop.tag.WebDesktopTag;

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
public class PageTag extends WebDesktopTag<Form> {

  private static final long serialVersionUID = 1L;

  @Override
  protected int startTag() throws Throwable {
    writeln("");
    writeln(" <!-- Page wrapper -->");
    writeln(" <div id=\"wrapper\">");

    return EVAL_BODY_INCLUDE;
  }

  @Override
  protected void endTag() throws Throwable {
    write(" </div>");

    writeln(" <!-- Scroll to Top Button-->");
    writeln(" <a class=\"scroll-to-top rounded\" href=\"#page-top\"> <i class=\"fas fa-angle-up\"></i></a>");
    writeln("");

    writeln(" <!-- Logout Modal-->");
    writeln(" <div class=\"modal fade\" id=\"logoutModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">");
    writeln("   <div class=\"modal-dialog\" role=\"document\">");
    writeln("     <div class=\"modal-content\">");
    writeln("       <div class=\"modal-header\">");
    writeln("         <h5 class=\"modal-title\" id=\"exampleModalLabel\">Vuoi uscire?</h5>");
    writeln("         <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">");
    writeln("           <span aria-hidden=\"true\">×</span>");
    writeln("         </button>");
    writeln("       </div>");
    writeln("       <div class=\"modal-body\">Selezionare \"Esci\" per terminare la sessione corrente.</div>");
    writeln("       <div class=\"modal-footer\">");
    writeln("         <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Annulla</button>");
    writeln("         <a class=\"btn btn-primary\" href=\"../sec/logout\">Esci</a>");
    writeln("       </div>");
    writeln("     </div>");
    writeln("   </div>");
    writeln(" </div>");

    writeln(" <script src=\"../vendor/jquery/jquery.min.js\"></script>");
    writeln(" <script src=\"../vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>");
    writeln(" <script src=\"../vendor/jquery-easing/jquery.easing.min.js\"></script>");
    writeln(" <script src=\"../vendor/jquery-autocomplete/jquery.autocomplete.js\"></script>");
    writeln(" <script src=\"../js/sb-admin-2.min.js\"></script>");
    writeln(" <script src=\"../js/web-desktop.js\"></script>");

    writeln(" <script src=\"../vendor/chart.js/Chart.min.js\"></script>");
    writeln(" <script src=\"../js/web-desktop-chart.js\"></script>");
  }

}
