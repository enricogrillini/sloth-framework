package it.eg.sloth.dbmodeler;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

@Disabled
class PostgresDbReaderTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException {
        init("jdbc:postgresql://localhost:5432/postgres");

        //init("jdbc:h2:mem:outputDb;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:dbmodeler/POSTGRES-create.sql'", DataBaseType.POSTGRES);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.POSTGRES, "gildace");
    }

}
