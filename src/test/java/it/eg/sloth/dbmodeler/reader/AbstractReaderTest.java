package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.writer.DbSchemaWriter;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.framework.utility.table.DataTableUtil;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

@Getter
@Setter
public abstract class AbstractReaderTest {

    private Connection connection;

    private String owner;

    private DbSchemaReader dbSchemaReader;

    protected DataBase readSchemaTest(DataBaseType dataBaseType) throws SQLException, IOException, FrameworkException {
        Schema schema = getDbSchemaReader().refreshSchema(getConnection(), getOwner());
        Assertions.assertEquals(11, schema.getTableCollection().size());
        Assertions.assertEquals(2, schema.getSequenceCollection().size());

        DbConnection dbConnection = DbConnection.builder()
                .name("gildaceOn" + dataBaseType)
                .dataBaseType(dataBaseType)
                .dbOwner(getOwner())
                .build();

        DataBase dataBase = new DataBase();
        dataBase.setDbConnection(dbConnection);
        dataBase.setSchema(schema);

        try (StringWriter writer = new StringWriter()) {
            dataBase.writeJson(writer);
            String actual = ResourceUtil.normalizeString(writer.toString());

            String expected = ResourceUtil.normalizedResourceAsString("dbmodeler/" + dataBaseType + "-db.json");
            Assertions.assertEquals(expected, actual);
        }

        return dataBase;
    }

    void generateDbModeler(DataBase dataBase) throws IOException, FrameworkException, SQLException {
        TestFactory.createOutputDir();
        TestFactory.createOutputDir("dbmodeler");
        DataBaseType dataBaseType = dataBase.getDbConnection().getDataBaseType();

        // XXX-db.json
        dataBase.writeJson(TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-db.json");

        // XXX-tables.json
        DataTable data;
        data = getDbSchemaReader().tablesData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-tables.json");

        // XXX-constraints.json
        data = getDbSchemaReader().constraintsData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-constraints.json");

        // XXX-indexes.json
        data = getDbSchemaReader().indexesData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-indexes.json");

        // // XXX-sequences.json
        data = getDbSchemaReader().sequencesData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-sequences.json");
    }

    void generateSnippetSql(DataBase dataBase) throws IOException {
        TestFactory.createOutputDir();
        TestFactory.createOutputDir("snippet-sql");
        Schema schema = dataBase.getSchema();
        DataBaseType dataBaseType = dataBase.getDbConnection().getDataBaseType();

        DbSchemaWriter dbSchemaWriter = DbSchemaWriter.Factory.getSchemaWriter(dataBaseType);
        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-tables.sql")) {
            fileWriter.write(dbSchemaWriter.sqlTables(schema, true, true));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-tables-base.sql")) {
            fileWriter.write(dbSchemaWriter.sqlTables(schema, false, false));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-indexes.sql")) {
            fileWriter.write(dbSchemaWriter.sqlIndexes(schema, true, true));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-indexes-base.sql")) {
            fileWriter.write(dbSchemaWriter.sqlIndexes(schema, false, false));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-primarykey.sql")) {
            fileWriter.write(dbSchemaWriter.sqlPrimaryKey(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-foreignkey.sql")) {
            fileWriter.write(dbSchemaWriter.sqlForeignKey(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-sequences.sql")) {
            fileWriter.write(dbSchemaWriter.sqlSequences(schema));
        }
    }

}
