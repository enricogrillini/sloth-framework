package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.TestFactory;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.dbmodeler.model.statistics.Statistics;
import it.eg.sloth.dbmodeler.writer.DbSchemaWriter;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.framework.utility.table.DataTableUtil;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Getter
@Setter
public abstract class AbstractReaderTest {

    private Connection connection;

    private String owner;

    private DbSchemaReader dbSchemaReader;

    void initMock(DataBaseType dataBaseType) throws IOException, SQLException, FrameworkException {
        String baseName = "dbmodeler/" + dataBaseType.name() + "-";

        // Mock connection
        setConnection(null);
        setOwner("gildace");

        // Mock reader
        DbSchemaReader dbSchemaReader;
        switch (dataBaseType) {
            case ORACLE:
                dbSchemaReader = Mockito.mock(OracleSchemaReader.class);
                break;
            case POSTGRES:
                dbSchemaReader = Mockito.mock(PostgresSchemaReader.class);
                break;
            default:
                dbSchemaReader = null;
        }

        Mockito.doCallRealMethod().when(dbSchemaReader).refreshSchema(Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addTables(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addConstraints(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addIndexes(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addConstants(Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addSequences(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addViews(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addStoredProcedure(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).calcColumnType(Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).refreshStatistics(Mockito.any(), Mockito.any());


        Mockito.when(dbSchemaReader.tablesData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "tables.json"));
        Mockito.when(dbSchemaReader.constraintsData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "constraints.json"));
        Mockito.when(dbSchemaReader.indexesData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "indexes.json"));

        Mockito.when(dbSchemaReader.constantsData(Mockito.any(), Mockito.eq("Sec_Dec_Funzioni"), Mockito.any())).thenReturn(readData(baseName + "constants-sec_dec_funzioni.json"));
        Mockito.when(dbSchemaReader.constantsData(Mockito.any(), Mockito.eq("Sec_Dec_Menu"), Mockito.any())).thenReturn(readData(baseName + "constants-sec_dec_menu.json"));
        Mockito.when(dbSchemaReader.constantsData(Mockito.any(), Mockito.eq("Sec_Dec_Menuutente"), Mockito.any())).thenReturn(readData(baseName + "constants-sec_dec_menuutente.json"));
        Mockito.when(dbSchemaReader.constantsData(Mockito.any(), Mockito.eq("Sec_Dec_Ruoli"), Mockito.any())).thenReturn(readData(baseName + "constants-sec_dec_ruoli.json"));
        Mockito.when(dbSchemaReader.constantsData(Mockito.any(), Mockito.eq("Sec_Dec_Tipivoce"), Mockito.any())).thenReturn(readData(baseName + "constants-sec_dec_tipivoce.json"));

        Mockito.when(dbSchemaReader.sequencesData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "sequences.json"));
        Mockito.when(dbSchemaReader.viewsData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "views.json"));
        Mockito.when(dbSchemaReader.viewsColumnsData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "views-columns.json"));
        Mockito.when(dbSchemaReader.storedProcedureData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "storedprocedures.json"));
        Mockito.when(dbSchemaReader.statisticsData(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "statistics.json"));

        if (dbSchemaReader instanceof OracleSchemaReader) {
            OracleSchemaReader oracleSchemaReader = (OracleSchemaReader) dbSchemaReader;
            Mockito.when(oracleSchemaReader.sourcesArguments(Mockito.any(), Mockito.any())).thenReturn(readData(baseName + "sourcesarguments.json"));
        }

        setDbSchemaReader(dbSchemaReader);
    }

    protected DataBase readSchemaTest(DataBaseType dataBaseType) throws SQLException, IOException, FrameworkException {
        Schema schema = getDbSchemaReader().refreshSchema(getConnection(), getOwner());
        Assertions.assertEquals(12, schema.getTableCollection().size());
        Assertions.assertEquals(3, schema.getSequenceCollection().size());

        Statistics statistics = getDbSchemaReader().refreshStatistics(getConnection(), getOwner());
        assertEquals(12, statistics.getTableCount());
        assertEquals(18, statistics.getIndexCount());

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

        // XXX-sequences.json
        data = getDbSchemaReader().sequencesData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-sequences.json");

        // XXX-constant-TTT.json
        for (Table table : dataBase.getSchema().getTableCollection()) {
            if (table.isDecodeTable()) {
                data = getDbSchemaReader().constantsData(getConnection(), table.getName(), table.getTableColumn(0).getName());
                DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-constants-" + table.getName().toLowerCase() + ".json");
            }
        }

        // XXX-views.json
        data = getDbSchemaReader().viewsData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-views.json");

        data = getDbSchemaReader().viewsColumnsData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-views-columns.json");

        // XXX-sequences.json
        data = getDbSchemaReader().statisticsData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-statistics.json");

        // XXX-sequences.json
        data = getDbSchemaReader().storedProcedureData(getConnection(), getOwner());
        DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-storedprocedures.json");


        if (getDbSchemaReader() instanceof OracleSchemaReader) {
            OracleSchemaReader oracleSchemaReader = (OracleSchemaReader) getDbSchemaReader();

            // XXX-sequences.json
            data = oracleSchemaReader.sourcesArguments(getConnection(), getOwner());
            DataTableUtil.saveDataToJsonFile(data, TestFactory.OUTPUT_DIR + "/dbmodeler/" + dataBaseType + "-sourcesarguments.json");
        }

    }


    DataTable readData(String fileName) throws IOException {
        DataTable table = new it.eg.sloth.db.datasource.table.Table();
        DataTableUtil.loadDataJsonFile(table, ResourceUtil.resourceFile(fileName));

        return table;
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
            fileWriter.write(dbSchemaWriter.sqlPrimaryKeys(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-foreignkey.sql")) {
            fileWriter.write(dbSchemaWriter.sqlForeignKeys(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-foreignkey.sql")) {
            fileWriter.write(dbSchemaWriter.sqlForeignKeys(schema));
        }


        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-sequences.sql")) {
            fileWriter.write(dbSchemaWriter.sqlSequences(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-functions.sql")) {
            fileWriter.write(dbSchemaWriter.sqlFunction(schema));
        }

        try (FileWriter fileWriter = new FileWriter(TestFactory.OUTPUT_DIR + "/snippet-sql/" + dataBaseType + "-procedures.sql")) {
            fileWriter.write(dbSchemaWriter.sqlProcedure(schema));
        }
    }

}
