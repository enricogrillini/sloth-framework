# Sloth framework

## Repo Maven artefatti

Gli artefatti del framework sono rilasciati su GitHub ed in particolare sono regolarmente versionati i pacchetti:

- sloth-framework

  ```xml
  <groupId>it.eg.sloth</groupId>
  <artifactId>sloth-framework</artifactId>
  <version>2.0.0-SNAPSHOT</version>
  ```

- sloth-plugin

  ```xml
  <groupId>it.eg.sloth</groupId>
  <artifactId>sloth-plugin</artifactId>
  <version>2.0.0-SNAPSHOT</version>
  ```



Per poter accedere al repository maven di GitHub è necessario ottenere un token GitHub (avendo preventivamente creato un proprio account) e configurare il file `settings.xml` come illustrato nel seguito. I placeholder USERNAME e TOKEN devono essere modificati con le proprie credenziali

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
          <releases><enabled>true</enabled></releases>
          <snapshots><enabled>true</enabled></snapshots>
        </repository>
        <repository>
          <id>github</id>
          <name>GitHub OWNER Apache Maven Packages</name>
          <url>https://maven.pkg.github.com/enricogrillini/sloth-framework</url>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username>USERNAME</username>
      <password>TOKEN</password>
    </server>
  </servers>
</settings>
```

