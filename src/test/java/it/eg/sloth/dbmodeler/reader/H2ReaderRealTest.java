package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

class H2ReaderRealTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException, FrameworkException {
        DriverManager.setLoginTimeout(1);

        setConnection(DriverManager.getConnection("jdbc:h2:mem:outputDbH2;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:dbmodeler/H2-create.sql'", "gildace", "gildace"));
        setOwner("PUBLIC");
        setDbSchemaReader(DbSchemaReader.Factory.getDbSchemaReader(DataBaseType.H2));
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        DataBase dataBase =   super.readSchemaTest(DataBaseType.H2);

        generateDbModeler(dataBase);
        generateSnippetSql(dataBase);
    }
}
