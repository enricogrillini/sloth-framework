package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.statistics.Statistics;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void readStatisticsTest() throws SQLException, IOException, FrameworkException {
        Statistics statistics = getDbSchemaReader().refreshStatistics(getConnection(), getOwner());

        assertEquals("org.postgresql.Driver", statistics.getDriverClass());
        assertEquals(13, statistics.getTableCount());
        assertEquals(20, statistics.getIndexCount());
    }

}
