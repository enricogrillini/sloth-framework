package it.eg.sloth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
public abstract class AbstractTest {

    // {0}: ClassName
    // {1}: expected|mock
    // {2}: FileName
    protected static final String FILE_PATH = "src/test/test-file/{0}/{1}/{2}";

    private TestInfo testInfo;

    protected String readMockFile(String name) {
        return readFile("mock", name);
    }

    protected String readExpectedFile(String name) {
        return readFile("expected", name);
    }

    private File getFile(String dirPrefix, String fileName) {
        String className = getClass().getSimpleName();
        return new File(MessageFormat.format(FILE_PATH, className, dirPrefix, fileName));
    }

    private String readFile(String dirPrefix, String fileName) {
        File readFile = getFile(dirPrefix, fileName);
        if (readFile.isFile()) {
            try {
                return FileUtils.readFileToString(readFile, StandardCharsets.UTF_8);
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
                fail(ex);
            }
        }
        fail("File " + readFile + (readFile.exists() ? " non leggibile" : " non trovato"));
        return null;            // Per compilazione. Non arriveremo mai qui
    }

    protected void createFile(String dirPrefix, String fileName, String payload) throws IOException {
        FileUtils.writeStringToFile(getFile(dirPrefix, fileName), payload, StandardCharsets.UTF_8.toString(), false);
    }

    protected void assertEqualsStr(String expectedFileName, String actualStr) {
        String expectedStr = readExpectedFile(expectedFileName).replace("\r\n", "\n");
        actualStr = actualStr == null ? "" : actualStr.replace("\r\n", "\n");

        assertEquals(expectedStr, actualStr);
    }

//    protected void assertEqualsJson(String expectedFileName, Object actual, String... ignoreFields) throws JsonProcessingException {
//        String expectedStr = readExpectedFile(expectedFileName);
//        String actualStr = objectMapper.writeValueAsString(actual);
//
//        try {
//            try {
//                // "STRICT" pro fallimento test in presenza di campi aggiuntivi
//                if (ignoreFields == null || ignoreFields.length == 0) {
//                    JSONAssert.assertEquals(expectedStr, actualStr, JSONCompareMode.STRICT);
//                } else {
//                    Customization[] customizationsArray = new Customization[ignoreFields.length];
//                    for (int i = 0; i < ignoreFields.length; i++) {
//                        customizationsArray[i] = new Customization(ignoreFields[i], (o1, o2) -> true);
//                    }
//                    JSONAssert.assertEquals(expectedStr, actualStr, new CustomComparator(JSONCompareMode.STRICT, customizationsArray));
//                }
//
//            } catch (AssertionError ex) {
//                String fileNamePath = "./target/actual/{0}/{1}";
//                String className = getClass().getSimpleName();
//
//                FileUtils.writeStringToFile(new File(MessageFormat.format(fileNamePath, className, expectedFileName)), actualStr, StandardCharsets.UTF_8);
//                fail(ex);
//            }
//        } catch (JSONException | IOException ex) {
//            log.error(ex.getMessage(), ex);
//            fail(ex);
//        }
//    }
}
