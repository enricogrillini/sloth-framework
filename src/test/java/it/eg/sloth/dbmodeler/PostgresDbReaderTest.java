package it.eg.sloth.dbmodeler;

import it.eg.sloth.TestUtil;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

@Ignore
public class PostgresDbReaderTest {

    private static final String POSTGRES_DATABASE_JSON = TestUtil.OUTPUT_DIR + "/postgresDataBase.json";

    private DataBase dataBase;

    @Before
    public void init() throws IOException {
        DbConnection dbConnection = DbConnection.builder()
                .jdbcUrl("jdbc:postgresql://localhost:5432/postgres")
                .dbUser("postgres")
                .dbPassword("example")
                .dbOwner("PUBLIC")
                .dataBaseType(DataBaseType.POSTGRES)
                .build();

        dataBase = new DataBase();
        dataBase.setDbConnection(dbConnection);

        TestUtil.createOutputDir();
    }

    @Test
    public void readSchemaTest() throws SQLException, IOException, FrameworkException, ClassNotFoundException {
        DbSchemaReader dbReader = DbSchemaReader.Factory.getDbSchemaReader(dataBase.getDbConnection());
        dbReader.refreshSchema(dataBase);
        dbReader.writeFile(POSTGRES_DATABASE_JSON, dataBase);

        System.out.println(dbReader.writeString(dataBase));
    }

}
