package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class OracleReaderMockTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException, FrameworkException {
        initMock(DataBaseType.ORACLE);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.ORACLE);
    }


}
