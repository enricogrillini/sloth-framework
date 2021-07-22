package it.eg.sloth.dbmodeler;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;

class H2DbReaderTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException {
        init(DataBaseType.H2);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.H2, "PUBLIC");
    }
}
