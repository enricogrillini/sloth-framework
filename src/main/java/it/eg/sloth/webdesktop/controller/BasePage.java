package it.eg.sloth.webdesktop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import it.eg.sloth.framework.monitor.MonitorSingleton;
import it.eg.sloth.webdesktop.WebDesktopConstant;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Classe base da cui derivare tutte le classi di Controller Gestisce: - il
 * trace Applicativo - la sicurezza di base - WebDesktopDto
 * 
 * 
 * @author Enrico Grillini
 * 
 */
@Slf4j
public abstract class BasePage extends BaseController implements Controller {

  public BasePage() {
    super();
  }

  /**
   * Definisce la funzionalità a cui si deve essere abilitati per accedere al
   * controllo. Se non si vogliono effttuare controlli di sicurezza ritornare
   * "null"
   * 
   * @return
   */
  public abstract String getFunctionName();

  /**
   * Gestisce la risposta alla chiamata
   * 
   * @return
   * @throws Exception
   */
  public abstract ModelAndView service() throws Exception;

  public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse res) throws Exception {
    long eventid = 0;
    try {
      log.info("IN " + getClass().getName());

      // Inizializzo la pagina
      init(req, res);

      // Traccio il tempo di attraversamento
      eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.PAGE, getClass().getName(), getUser());

      if (redirectToHome()) {
        // Verifico se effettuare il redirect alla Home page
        log.info("Redirect to Home");
        return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

      } else if (!accessAllowed()) {
        // Verifico la sicurezza
        log.warn("Accesso non consentito. Redirect to Home");
        return new ModelAndView("redirect:" + WebDesktopConstant.Page.HOME_PUBBLICA_PAGE);

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

}
