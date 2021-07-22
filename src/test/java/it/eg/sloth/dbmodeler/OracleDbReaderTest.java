package it.eg.sloth.dbmodeler;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class OracleDbReaderTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException {
        //init("jdbc:oracle:thin:@localhost:1521:XE");
        init(DataBaseType.ORACLE);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.ORACLE, "gildace");
    }


}
