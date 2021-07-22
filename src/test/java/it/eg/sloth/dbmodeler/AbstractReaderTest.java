package it.eg.sloth.dbmodeler;

import it.eg.sloth.TestFactory;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractReaderTest {

    private Connection connection;

    void init(DataBaseType dataBaseType) throws IOException, SQLException {
        DriverManager.setLoginTimeout(1);

        String type = dataBaseType.name().toUpperCase();
        connection = DriverManager.getConnection("jdbc:h2:mem:outputDb" + type + ";MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:dbmodeler/" + type + "-create.sql'", "gildace", "gildace");

        TestFactory.createOutputDir();
    }

    void readSchemaTest(DataBaseType dataBaseType, String owner) throws SQLException, IOException, FrameworkException {
        DbSchemaReader dbReader = DbSchemaReader.Factory.getDbSchemaReader(dataBaseType, owner);

        Schema schema = dbReader.refreshSchema(connection);
        Assertions.assertEquals(11, schema.getTableCollection().size());
        Assertions.assertEquals(2, schema.getSequenceCollection().size());

        DataBase dataBase = new DataBase();
        dataBase.setSchema(schema);
        dataBase.writeJsonFile(TestFactory.OUTPUT_DIR + "/" + dataBaseType + "-db.json");
    }

}
