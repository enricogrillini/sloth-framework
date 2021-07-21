package it.eg.sloth.dbmodeler;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.writer.DbSchemaWriter;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

class OracleDbWriterTest extends AbstractWriterTest {

    @BeforeEach
    void init() throws IOException {
        init(DataBaseType.ORACLE);
    }

    @Test
    void writeSchemaTest() throws SQLException, IOException, FrameworkException {
        super.writeSchemaTest();

        DbSchemaWriter postgresSchemaWriter = DbSchemaWriter.Factory.getDbSchemaReader(DataBaseType.POSTGRES, "gildace");
        System.out.println(postgresSchemaWriter.sqlSequences(dataBase.getSchema()));
        System.out.println(postgresSchemaWriter.sqlTables(dataBase.getSchema(), false, false));
    }

}
