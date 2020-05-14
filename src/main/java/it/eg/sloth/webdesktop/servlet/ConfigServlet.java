package it.eg.sloth.webdesktop.servlet;

import java.io.File;
import java.net.URISyntaxException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import it.eg.sloth.framework.configuration.ConfigSingleton;
import it.eg.sloth.framework.model.Initializer;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Enrico Grillini
 * 
 */
@Slf4j
public class ConfigServlet extends HttpServlet {

  public static final String SYSTEM_CONFIG = "/WEB-INF/system.xml";

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    // Leggo i file di configurazione
    String rootPath = getServletContext().getRealPath("/");
    String fileName = getServletContext().getRealPath("/") + SYSTEM_CONFIG;
    
    log.info("Configurazione applicativa - rootPath {}, system.xml {}", rootPath, fileName);
    try {
      ConfigSingleton.initInstance(new File(rootPath));
    } catch (URISyntaxException e) {
      throw new ServletException(e);
    }

    // Eseguo gli initializers
    log.info("Initializers");
    for (String string : ConfigSingleton.getInstance().getInitializersClass()) {
      log.info("Initializer: {}", string);

      try {
        Initializer initializer = (Initializer) Class.forName(string).newInstance();
        initializer.execute();
      } catch (Exception e) {
        log.error("Initializer: {}", string, e);
      }
    }
  }

}
