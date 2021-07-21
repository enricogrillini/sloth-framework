package it.eg.sloth.dbmodeler;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class H2DbWriterTest extends AbstractWriterTest {

    @BeforeEach
    void init() throws IOException {
        init(DataBaseType.H2);
    }

    @Test
    void writeSchemaTest() throws SQLException, IOException, FrameworkException {
        super.writeSchemaTest();
    }

}
