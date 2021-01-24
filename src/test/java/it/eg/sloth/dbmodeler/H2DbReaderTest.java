package it.eg.sloth.dbmodeler;

import it.eg.sloth.TestUtil;
import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class H2DbReaderTest {

    private static final String H2_DATABASE_JSON = TestUtil.OUTPUT_DIR + "/h2DataBase.json";

    private DataBase dataBase;

    @Before
    public void init() throws IOException {
        // Persistenza semplice su file
        // jdbc:h2:file:./outputDb;MODE=DB2;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:create.sql'

        // Persistenza semplice in memory
        // jdbc:h2:mem:outputDb;MODE=DB2;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:create.sql'

        DbConnection dbConnection = DbConnection.builder()
                .jdbcUrl("jdbc:h2:mem:outputDb;MODE=DB2;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:dbmodeler/create.sql'")
                .dbUser("sa")
                .dbPassword("")
                .dbOwner("PUBLIC")
                .dataBaseType(DataBaseType.H2)
                .build();

        dataBase = new DataBase();
        dataBase.setDbConnection(dbConnection);

        TestUtil.createOutputDir();
    }

    @Test
    public void readSchemaTest() throws SQLException, IOException, FrameworkException, ClassNotFoundException {
        DbSchemaReader dbReader = DbSchemaReader.Factory.getDbSchemaReader(dataBase.getDbConnection());
        dbReader.refreshSchema(dataBase);
        dbReader.writeFile(H2_DATABASE_JSON, dataBase);

        System.out.println(dbReader.writeString(dataBase));
    }

//    public void listAllTables(DatabaseMetaData databaseMetaData) {
//        try {
//            //Print TABLE_TYPE "TABLE"
//            ResultSet resultSet = databaseMetaData.getTables(null, null, null, null);
//            System.out.println("Printing TABLE_TYPE \"TABLE\" ");
//            System.out.println("----------------------------------");
//            while (resultSet.next()) {
//                //Print
//                System.out.println(resultSet.getString("TABLE_NAME"));
//            }
//
//            //Print TABLE_TYPE "SYSTEM TABLE"
//            resultSet = databaseMetaData.getTables(null, null, null, new String[]{"SYSTEM TABLE"});
//            System.out.println("Printing TABLE_TYPE \"SYSTEM TABLE\" ");
//            System.out.println("----------------------------------");
//            while (resultSet.next()) {
//                //Print
//                System.out.println(resultSet.getString("TABLE_NAME"));
//            }
//
//            //Print TABLE_TYPE "VIEW"
//            resultSet = databaseMetaData.getTables(null, null, null, new String[]{"VIEW"});
//            System.out.println("Printing TABLE_TYPE \"VIEW\" ");
//            System.out.println("----------------------------------");
//            while (resultSet.next()) {
//                //Print
//                System.out.println(resultSet.getString("TABLE_NAME"));
//            }
//
//
//        } catch (SQLException ex) {
//            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
//            System.exit(-1);
//        } catch (Exception ex) {
//            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
//            System.exit(-1);
//        }
//
//    }

}
