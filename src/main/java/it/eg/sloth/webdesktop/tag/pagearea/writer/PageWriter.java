package it.eg.sloth.webdesktop.tag.pagearea.writer;

public class PageWriter {
    private PageWriter() {
        // NOP
    }

    public static final String MODAL_LOGOUT =
            " <!-- Logout Modal-->\n" +
                    " <div class=\"modal fade\" id=\"logoutModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                    "   <div class=\"modal-dialog\" role=\"document\">\n" +
                    "     <div class=\"modal-content\">\n" +
                    "       <div class=\"modal-header\">\n" +
                    "         <h5 class=\"modal-title\" id=\"exampleModalLabel\">Vuoi uscire?</h5>\n" +
                    "         <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                    "           <span aria-hidden=\"true\">×</span>\n" +
                    "         </button>\n" +
                    "       </div>\n" +
                    "       <div class=\"modal-body\">Selezionare \"Esci\" per terminare la sessione corrente.</div>\n" +
                    "       <div class=\"modal-footer\">\n" +
                    "         <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Annulla</button>\n" +
                    "         <a class=\"btn btn-primary\" href=\"../sec/logout\">Esci</a>\n" +
                    "       </div>\n" +
                    "     </div>\n" +
                    "   </div>\n" +
                    " </div>\n";

    public static final String MODAL_JOB =
            " <!-- Job Modal-->\n" +
                    " <div class=\"modal fade\" id=\"jobModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                    "  <div class=\"modal-dialog\" role=\"document\">\n" +
                    "   <div class=\"modal-content\">\n" +
                    "    <div class=\"modal-header\">\n" +
                    "     <h5 class=\"modal-title\" id=\"jobHeader\">Job XXX</h5>\n" +
                    "     <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                    "      <span aria-hidden=\"true\">×</span>\n" +
                    "     </button>\n" +
                    "    </div>\n" +
                    "    <div id=\"jobStartBody\" class=\"modal-body\"><div class=\"alert\"><h4 id=\"jobStartText\" class=\"alert-heading\">Vuoi avviare il Job XXXX</h4></div></div>\n" +
                    "    <div id=\"jobStartFooter\" class=\"modal-footer\"><button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Annulla</button><button class=\"btn btn-primary\" type=\"button\" onclick=\"startJob()\">Avvia</button></div>\n" +
                    "    <div id=\"jobStatusBody\" class=\"modal-body\">\n" +
                    "     <div class=\"alert alert-success\">\n" +
                    "      <h4 id=\"jobStatus\" class=\"alert-heading\">Aa ashd fh</h4>\n" +
                    "      <p id=\"jobMessage\" class=\"mb-0  font-weight-bold\">Il campo inputNUMBER non &egrave; un numero valido.</p>\n" +
                    "     </div>\n" +
                    "     <div class=\"progress\"><div id=\"jobProgessBar\" class=\"progress-bar progress-bar-striped bg-primary\" role=\"progressbar\" style=\"width: 80%\" aria-valuenow=\"50\" aria-valuemin=\"0\" aria-valuemax=\"100\"></div></div>\n" +
                    "    </div>\n" +
                    "    <div id=\"jobStatusFooter\" class=\"modal-footer\"><button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Chiudi</button></div>\n" +
                    "   </div>\n" +
                    "  </div>\n" +
                    " </div>\n";

    public static final String MODAL_ALERT =
            " <!-- AlertModal -->\n" +
                    " <div class=\"modal fade\" id=\"alertModal\" tabindex=\"-1\" role=\"dialog\">\n" +
                    "  <div class=\"modal-dialog modal-dialog-centered\" role=\"document\">\n" +
                    "   <div class=\"modal-content\">\n" +
                    "    <div class=\"modal-header\">\n" +
                    "     <h5 id=\"alertModalTitle\" class=\"modal-title text-primary\"></h5>\n" +
                    "     <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                    "      <span aria-hidden=\"true\">&times;</span>\n" +
                    "     </button>\n" +
                    "    </div>\n" +
                    "    <div id=\"alertModalDescription\" class=\"modal-body\"></div>\n" +
                    "   </div>\n" +
                    "  </div>\n" +
                    " </div>";

    public static final String modalLogout() {
        return MODAL_LOGOUT;
    }

    public static final String modalJob() {
        return MODAL_JOB;
    }

    public static final String modalAlert() {
        return MODAL_ALERT;
    }
}
