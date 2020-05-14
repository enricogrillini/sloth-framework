# Configurazione Web App

Il framework prevede due file di configurazione:

- **system.xml** - indirizza gli aspetti propri del framework:
  
  - Environment
  - Initializer
  - Job
  - Parameter
  
  Può variare al variare dell'ambiente e pertanto può essere esternalizzato agendo sul parametro `configuration.external.path` su `application.properties`. Se questo parametro non è presente il file `system.xml` sarà ricercato all'interno del classpath.
  
- **application.properties** - Configurazioni applicative non mutabili da ambiente ad ambiente. Di seguito un esempio di file di properties:

  ```properties
  configuration.external.path=/opt/mount/gilda
  ```

  

  

