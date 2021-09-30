package it.eg.sloth.dbmodeler.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Data
public class DataBase implements JsonInterface {

    DbConnection dbConnection;
    Schema schema;

    public void refreshSchema() throws FrameworkException, SQLException, IOException {
        DbSchemaReader dbSchemaReader = DbSchemaReader.Factory.getDbSchemaReader(dbConnection.getDataBaseType());
        this.setSchema(dbSchemaReader.refreshSchemaByDbConnection(this.getDbConnection()));
    }

    public void readJson(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DataBase dataBase = mapper.readValue(file, DataBase.class);

        dbConnection = dataBase.getDbConnection();
        schema = dataBase.getSchema();
    }

    public void readJson(String fileName) throws IOException {
        readJson(new File(fileName));
    }

}
