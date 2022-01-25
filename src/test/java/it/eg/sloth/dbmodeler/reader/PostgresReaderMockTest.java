package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class PostgresReaderMockTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException, FrameworkException {
        initMock(DataBaseType.POSTGRES);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.POSTGRES);


        Schema schema = getDbSchemaReader().refreshSchema(getConnection(), getOwner());
        schema.getFunction("aaa");
    }

}
