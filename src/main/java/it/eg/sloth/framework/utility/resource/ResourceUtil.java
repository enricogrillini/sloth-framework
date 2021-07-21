package it.eg.sloth.framework.utility.resource;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceUtil {

    private ResourceUtil() {
        // NOP
    }

    public static File resourceFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);

        return new File(resource.getURI());
    }

    @SneakyThrows
    public static String resourceAsString(String path) {
        ClassPathResource resource = new ClassPathResource(path);

        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(bdata, StandardCharsets.UTF_8);
    }

    public static String normalizedResourceAsString(String path) {
        return resourceAsString(path).replace("\r\n", "\n");
    }

    public static String normalizedResourceAsString(byte[] resource) {
        return new String(resource, StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

}
