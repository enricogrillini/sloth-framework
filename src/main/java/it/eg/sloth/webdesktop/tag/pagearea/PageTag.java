package it.eg.sloth.webdesktop.tag.pagearea;

import it.eg.sloth.webdesktop.tag.WebDesktopTag;

public class PageTag extends WebDesktopTag {

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
    writeln("         <a class=\"btn btn-primary\" href=\"LogoutPage.html\">Esci</a>");
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
