package it.eg.sloth.dbmodeler;

import it.eg.sloth.TestUtil;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

@Disabled
class OracleDbReaderTest {


    private static final String ORACLE_DATABASE_JSON = TestUtil.OUTPUT_DIR + "/oracleDataBase.json";

    private DataBase dataBase;

    @BeforeEach
    void init() throws IOException {
        DbConnection dbConnection = DbConnection.builder()
                .jdbcUrl("jdbc:oracle:thin:@localhost:1521:XE")
                .dbUser("gilda")
                .dbPassword("gilda")
                .dbOwner("gilda")
                .dataBaseType(DataBaseType.ORACLE)
                .build();

        dataBase = new DataBase();
        dataBase.setDbConnection(dbConnection);

        TestUtil.createOutputDir();
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        DbSchemaReader dbReader = DbSchemaReader.Factory.getDbSchemaReader(dataBase.getDbConnection());
        dbReader.refreshSchema(dataBase);
        dbReader.writeFile(ORACLE_DATABASE_JSON, dataBase);

        System.out.println(dbReader.writeString(dataBase));
    }

}
