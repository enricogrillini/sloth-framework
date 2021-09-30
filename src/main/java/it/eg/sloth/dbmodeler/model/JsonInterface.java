package it.eg.sloth.dbmodeler.model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public interface JsonInterface {

    default void writeJson(File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writeJson(writer);
        }
    }

    default void writeJson(String fileName) throws IOException {
        writeJson(new File(fileName));
    }

    default void writeJson(Writer writer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

        ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(writer, this);
    }

}
