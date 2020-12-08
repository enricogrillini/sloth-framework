package it.eg.sloth.framework.utility.resource;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

public class ResourceUtil {

    private ResourceUtil() {
        // NOP
    }

    @SneakyThrows
    public static String resourceAsString(String path) {
        ClassPathResource resource = new ClassPathResource(path);

        byte[] bdata = FileCopyUtils.copyToByteArray(resource.getInputStream());
        return new String(bdata, StandardCharsets.UTF_8);
    }

}
