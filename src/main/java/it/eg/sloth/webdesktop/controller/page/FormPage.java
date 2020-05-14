package it.eg.sloth.webdesktop.controller.page;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.servlet.ModelAndView;

import it.eg.sloth.form.Form;
import it.eg.sloth.form.WebRequest;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.monitor.MonitorSingleton;
import it.eg.sloth.framework.security.User;
import it.eg.sloth.webdesktop.WebDesktopConstant;
import it.eg.sloth.webdesktop.controller.BasePage;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * Fornisce una prima implementazione della navigazione di base aggiungendo il
 * concetto di Form. Rispetto al BaseController aggiunge: - la gestione della
 * Form - la gestione della WebRequest - la gestione della MessageList -
 * definisce un utente base nel caso in cui non sia gia' definito
 *
 * @author Enrico Grillini
 *
 * @param <F>
 */
@Slf4j
public abstract class FormPage<F extends Form> extends BasePage {

    private WebRequest webRequest;
    private F form;
    private boolean newForm;

    public abstract F createForm();

    public FormPage() {
        super();

        this.webRequest = null;
        this.form = null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void init(HttpServletRequest req, HttpServletResponse res) throws UnsupportedEncodingException, FileUploadException {
        super.init(req, res);
        this.webRequest = new WebRequest(getRequest());

        if (!(getClass().getSimpleName() + ".html").equals(getWebDesktopDto().getLastController())) {
            getWebDesktopDto().setLastController(getClass().getSimpleName() + ".html");
            getWebDesktopDto().setForm(form = createForm());
            this.newForm = true;
        } else {
            form = (F) getWebDesktopDto().getForm();
            this.newForm = false;
        }
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
        long eventid = 0;
        try {
            log.info("IN " + getClass().getName());

            // Inizializzo la pagina
            init(req, res);

            // Traccio il tempo di attraversamento
            eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.PAGE, getClass().getName(), getUser());

            // Se l'utente non è presente in sessione lo creo
            if (getWebDesktopDto().getUser() == null) {
                getWebDesktopDto().setUser(new User());
            }

            if (redirectToHome()) {
                // Verifico se effettuare il redirect alla Home page
                log.info("Redirect to Home");
                return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

            } else if (!accessAllowed()) {
                // Verifico la sicurezza
                log.warn("Accesso non consentito");
                getMessageList().addBaseError("Accesso alla funzionalità non consentito!");
                return new ModelAndView(WebDesktopConstant.Jsp.ERROR);

            } else {
                return service();
            }

        } catch (Exception e) {
            log.error("ERROR " + getClass().getName(), e);
            return null;
        } finally {
            log.info("OUT " + getClass().getName());
            MonitorSingleton.getInstance().endEvent(eventid);
        }
    }

    public WebRequest getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(WebRequest webRequest) {
        this.webRequest = webRequest;
    }

    public F getForm() {
        return form;
    }

    public MessageList getMessageList() {
        return getWebDesktopDto().getMessageList();
    }

    public boolean isNewForm() {
        return newForm;
    }

    public void setNewForm(boolean newForm) {
        this.newForm = newForm;
    }
}
