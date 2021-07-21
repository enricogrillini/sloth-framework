package it.eg.sloth.dbmodeler.model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import lombok.Data;

import java.io.File;
import java.io.IOException;

@Data
public class DataBase {

    DbConnection dbConnection;
    Schema schema;

    public void readJsonFile(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DataBase dataBase = mapper.readValue(file, DataBase.class);

        dbConnection = dataBase.getDbConnection();
        schema = dataBase.getSchema();
    }

    public void writeJsonFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(fileName), this);
    }
}
