package it.eg.sloth.dbmodeler.writer;

import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.sql.SQLException;

public abstract class AbstractWriterTest {

    protected DataBase dataBase;
    protected DbSchemaWriter dbSchemaWriter;

    void init(DataBaseType dataBaseType) throws IOException {
        dataBase = new DataBase();
        dataBase.readJson(ResourceUtil.resourceFile("dbmodeler/" + dataBaseType + "-db.json"));

        dbSchemaWriter = DbSchemaWriter.Factory.getSchemaWriter(dataBase.getDbConnection().getDataBaseType());
    }

    void writeSchemaTest() throws SQLException, IOException, FrameworkException {
        DataBaseType dataBaseType = dataBase.getDbConnection().getDataBaseType();

        // Table
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-tables.sql"), dbSchemaWriter.sqlTables(dataBase.getSchema(), true, true));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-tables-base.sql"), dbSchemaWriter.sqlTables(dataBase.getSchema(), false, false));

        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-indexes.sql"), dbSchemaWriter.sqlIndexes(dataBase.getSchema(), true, true));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-indexes-base.sql"), dbSchemaWriter.sqlIndexes(dataBase.getSchema(), false, false));

        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-primarykey.sql"), dbSchemaWriter.sqlPrimaryKeys(dataBase.getSchema()));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-foreignkey.sql"), dbSchemaWriter.sqlForeignKeys(dataBase.getSchema()));

        // Sequence
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-sequences.sql"), dbSchemaWriter.sqlSequences(dataBase.getSchema()));

        // Stored procedure
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-views.sql"), dbSchemaWriter.sqlView(dataBase.getSchema()));

        // Stored procedure
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-procedures.sql"), dbSchemaWriter.sqlProcedure(dataBase.getSchema()));
        Assertions.assertEquals(ResourceUtil.normalizedResourceAsString("snippet-sql/" + dataBaseType + "-functions.sql"), dbSchemaWriter.sqlFunction(dataBase.getSchema()));

    }

}
