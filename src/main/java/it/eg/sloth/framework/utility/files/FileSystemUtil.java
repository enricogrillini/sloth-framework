package it.eg.sloth.framework.utility.files;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @author Enrico Grillini
 */
public class FileSystemUtil {

    private FileSystemUtil() {
        // NOP
    }

    /**
     * Ritorna un file dal contesto
     *
     * @param fileName
     * @return
     */
    public static final File getFileFromContext(String fileName) throws URISyntaxException {
        URL url = FileSystemUtil.class.getClassLoader().getResource(fileName);
        return Paths.get(url.toURI()).toFile();
    }

}
