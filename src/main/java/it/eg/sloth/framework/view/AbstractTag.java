package it.eg.sloth.framework.view;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Classe base per le Tag del Framework
 *
 * @author Enrico Grillini
 */
@Slf4j
public abstract class AbstractTag extends TagSupport {

    private static final long serialVersionUID = 1L;

    private String checkMessage;

    /**
     * Verifica che se le impostazioni della tag sono corrette
     *
     * @return
     */
    protected String check() {
        return null;
    }

    /**
     * Metodo richiamato da doStartTag
     *
     * @return
     */
    protected abstract int startTag() throws Throwable;

    /**
     * Metodo richiamato da doStartTag
     *
     * @return
     */
    protected abstract void endTag() throws Throwable;

    /**
     * Ritorna l'attributo specificato prelevandolo dalla session
     *
     * @param name
     * @return
     */
    protected Object getObjectFromSession(String name) {
        return pageContext.getSession().getAttribute(name);
    }

    /**
     * Stampa il testo passato
     *
     * @param testo
     */
    protected void write(String testo) throws IOException {
        pageContext.getOut().print(testo);
    }

    /**
     * Stampa una riga vuota
     */
    protected void writeln() throws IOException {
        pageContext.getOut().println();
    }

    /**
     * Stampa il testo passato
     *
     * @param testo
     */
    protected void writeln(String testo) throws IOException {
        pageContext.getOut().println(testo);
    }

    @Override
    public int doStartTag() {
        try {
            checkMessage = check();
            if (checkMessage == null)
                return startTag();
            else
                writeln("Errore nella tag " + getClass().getName() + ": " + checkMessage);

        } catch (Throwable e) {
            log.error("doStartTag {}", e.getMessage(), e);
            try {
                writeln("Errore nel metodo " + getClass().getName() + ".startTag: " + e.toString());
            } catch (IOException ex) {
                log.error("doStartTag", ex);
            }
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            if (checkMessage == null)
                endTag();

        } catch (Throwable e) {
            log.error("doEndTag\n", e);
            try {
                writeln("<!-- Errore nel metodo " + getClass().getName() + ".endTag: " + e.toString() + " -->");
            } catch (IOException ex) {
                log.error("doStartTag", ex);
            }

        }

        return super.doEndTag();

    }
}
