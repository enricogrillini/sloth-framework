package it.eg.sloth;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {

    public static final String OUTPUT_DIR = "target/test-output";

    public static void createOutputDir() throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIR));
    }
    
}
