package it.eg.sloth.framework.configuration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import it.eg.sloth.framework.utility.files.FileSystemUtil;

public class ConfigSingletonTest {

    @Test
    public void ConfigSingletonInitTest() throws JAXBException, URISyntaxException {
        File rootPath = FileSystemUtil.getFileFromContext(ConfigSingleton.APPLICATION_PROPERTIES).getParentFile();

        ConfigSingleton.initInstance(rootPath);

        assertEquals("DEVELOP", ConfigSingleton.getInstance().getEnvironment().value());

    }

}
