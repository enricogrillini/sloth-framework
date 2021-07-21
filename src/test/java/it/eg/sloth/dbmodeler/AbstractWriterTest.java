package it.eg.sloth.dbmodeler;

import it.eg.sloth.TestFactory;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.writer.DbSchemaWriter;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractWriterTest {

    protected DataBase dataBase;
    protected DbSchemaWriter dbSchemaWriter;

    void init(DataBaseType dataBaseType) throws IOException {
        dataBase = new DataBase();
        dataBase.readJsonFile(ResourceUtil.resourceFile("dbmodeler/" + dataBaseType + "-db.json"));

        dbSchemaWriter = DbSchemaWriter.Factory.getDbSchemaReader(dataBase.getDbConnection().getDataBaseType(), dataBase.getDbConnection().getDbOwner());
        TestFactory.createOutputDir();
    }

    void writeSchemaTest() throws SQLException, IOException, FrameworkException {
        DataBaseType dataBaseType = dataBase.getDbConnection().getDataBaseType();

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/" + dataBaseType + "-sequence.sql")) {
            fileWriter.write(dbSchemaWriter.sqlSequences(dataBase.getSchema()));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/" + dataBaseType + "-tables.sql")) {
            fileWriter.write(dbSchemaWriter.sqlTables(dataBase.getSchema(), true, true));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/" + dataBaseType + "-tables-base.sql")) {
            fileWriter.write(dbSchemaWriter.sqlTables(dataBase.getSchema(), false, false));
        }

        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-sequences.sql"), dbSchemaWriter.sqlSequences(dataBase.getSchema()));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-tables.sql"), dbSchemaWriter.sqlTables(dataBase.getSchema(), true, true));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-tables-base.sql"), dbSchemaWriter.sqlTables(dataBase.getSchema(), false, false));
    }

}
