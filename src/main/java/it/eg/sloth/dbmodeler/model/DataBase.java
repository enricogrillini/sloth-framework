package it.eg.sloth.dbmodeler.model;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@Data
public class DataBase {

    DbConnection dbConnection;
    Schema schema;

    public void readJson(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DataBase dataBase = mapper.readValue(file, DataBase.class);

        dbConnection = dataBase.getDbConnection();
        schema = dataBase.getSchema();
    }

    public void readJson(String fileName) throws IOException {
        readJson(new File(fileName));
    }

    public void refreshSchema() throws FrameworkException, SQLException, IOException {
        DbSchemaReader dbSchemaReader = DbSchemaReader.Factory.getDbSchemaReader(dbConnection.getDataBaseType());
        this.setSchema(dbSchemaReader.refreshSchemaByDbConnection(this.getDbConnection()));
    }

    public void writeJson(File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            writeJson(writer);
        }
    }

    public void writeJson(String fileName) throws IOException {
        writeJson(new File(fileName));
    }

    public void writeJson(Writer writer) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);

        ObjectWriter objectWriter = mapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(writer, this);
    }
}
