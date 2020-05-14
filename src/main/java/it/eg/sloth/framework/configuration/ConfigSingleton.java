package it.eg.sloth.framework.configuration;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.utility.files.FileSystemUtil;
import it.eg.sloth.jaxb.config.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

/**
 * 
 * Singleton per la gestione della configurazione applicativa
 * 
 * @author Enrico Grillini
 * 
 */
@Slf4j
public class ConfigSingleton extends FrameComponent {

  public static final String APPLICATION_PROPERTIES = "application.properties";

  public static final String FRAMEWORK_EXTERNAL_FILE = "sloth.external.file";
  public static final String FRAMEWORK_DOCUMENTATION_URL = "sloth.documentation.url";

  public static final String FRAMEWORK_EXTERNAL_FILE_DEFAULT = "/system.xml";
  
  @Getter
  private File rootPath;

  @Getter
  private Properties properties;

  @Getter
  private Environment environment;

  private Map<String, String> initilizerMap;
  private Map<String, String> parameterMap;

  private static ConfigSingleton instance = null;

  private ConfigSingleton(File rootPath) throws URISyntaxException {
    this.rootPath = rootPath;

    // application.properties
    File propertiesFile = FileSystemUtil.getFileFromContext(APPLICATION_PROPERTIES);

    log.info("Read {} {}", APPLICATION_PROPERTIES, propertiesFile);
    properties = new Properties();
    try (FileInputStream inputStream = new FileInputStream(propertiesFile)) {
      properties.load(inputStream);
    } catch (IOException e) {
      log.error("Impossibile leggere il file di configurazione: {} ", propertiesFile.getAbsoluteFile(), e);
    }

    // system.xml
    File file;
    if (properties.containsKey(FRAMEWORK_EXTERNAL_FILE) && !BaseFunction.isBlank(properties.getProperty(FRAMEWORK_EXTERNAL_FILE))) {
      log.info("External configuration - file {}", properties.getProperty(FRAMEWORK_EXTERNAL_FILE));
      file = new File(properties.getProperty(FRAMEWORK_EXTERNAL_FILE));
    } else {
      log.info("Internal configuration - file {}", FRAMEWORK_EXTERNAL_FILE_DEFAULT);
      file = new File(rootPath.getAbsolutePath() + FRAMEWORK_EXTERNAL_FILE_DEFAULT);
    }

    try {
      JAXBContext jaxbContext = null;
      jaxbContext = JAXBContext.newInstance(Configuration.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      // Overloaded methods to unmarshal from different xml sources
      log.info("Caricamento file: {}", file);
      Configuration configuration = (Configuration) jaxbUnmarshaller.unmarshal(file);

      // Environment
      log.info("Environment");
      environment = configuration.getEnvironment();

      // Initilizer
      log.info("Initilizer");
      initilizerMap = new LinkedHashMap<>();
      for (Initializer group : configuration.getInitializers().getInitializer()) {
        initilizerMap.put(group.getName(), group.getClassName());
      }

      // Parameter
      log.info("Parameter");
      parameterMap = new LinkedHashMap<>();
      for (Group group : configuration.getParameters().getGroup()) {
        for (Parameter parameter : group.getParameter()) {
          parameterMap.put(group.getName() + "." + parameter.getName(), parameter.getValue());
        }
      }
    } catch (JAXBException e) {
      log.error("Impossibile leggere il file di configurazione: {} ", file.getAbsoluteFile(), e);
    }
  }

  public static ConfigSingleton getInstance() {
    return instance;
  }

  public static synchronized void initInstance(File rootPath) throws URISyntaxException {
    if (instance == null) {
      instance = new ConfigSingleton(rootPath);
    }
  }

  /**
   * Ritorna un parametro
   * 
   * @param gruop
   * @param name
   * @return
   */
  public String getParameter(String gruop, String name) {
    return parameterMap.get(gruop + "." + name);
  }

  /**
   * Ritorna il set degli initializer
   * 
   * @return
   */
  public Set<String> getInitializersClass() {
    return new LinkedHashSet<>(initilizerMap.values());
  }

  public String getProperty(String propertyKey) {
    return properties.getProperty(propertyKey);
  }

}
