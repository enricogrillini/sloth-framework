package it.eg.sloth.dbmodeler.model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
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
        ObjectMapper mapper = JsonMapper.builder()
                .configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true)
                .build();

        ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(writer, this);
    }

}
