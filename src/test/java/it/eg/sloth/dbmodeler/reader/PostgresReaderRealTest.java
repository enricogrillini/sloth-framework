package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

@Disabled
class PostgresReaderRealTest extends AbstractReaderTest {
    
    @BeforeEach
    void init() throws IOException, SQLException, FrameworkException {
        DriverManager.setLoginTimeout(1);
        setConnection(DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "gildace", "gildace"));
        setOwner("gildace");
        setDbSchemaReader(DbSchemaReader.Factory.getDbSchemaReader(DataBaseType.POSTGRES));
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        DataBase dataBase = super.readSchemaTest(DataBaseType.POSTGRES);

        generateDbModeler(dataBase);
        generateSnippetSql(dataBase);
    }

}
