package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import it.eg.sloth.framework.utility.table.DataTableUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;

class PostgresReaderMockTest extends AbstractReaderTest {

    @BeforeEach
    void init() throws IOException, SQLException, FrameworkException {
        // Mock connection
        setConnection(null);
        setOwner("gildace");

        // Mock reader
        DbSchemaReader dbSchemaReader = Mockito.mock(PostgresSchemaReader.class);
        Mockito.doCallRealMethod().when(dbSchemaReader).refreshSchema(Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addTables(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addConstraints(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addIndexes(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).addSequences(Mockito.any(), Mockito.any(), Mockito.any());
        Mockito.doCallRealMethod().when(dbSchemaReader).calcColumnType(Mockito.any());

        DataTable table = new Table();
        DataTableUtil.loadDataJsonFile(table, ResourceUtil.resourceFile("dbmodeler/POSTGRES-tables.json"));
        Mockito.when(dbSchemaReader.tablesData(Mockito.any(), Mockito.any())).thenReturn(table);

        table = new Table();
        DataTableUtil.loadDataJsonFile(table, ResourceUtil.resourceFile("dbmodeler/POSTGRES-constraints.json"));
        Mockito.when(dbSchemaReader.constraintsData(Mockito.any(), Mockito.any())).thenReturn(table);

        table = new Table();
        DataTableUtil.loadDataJsonFile(table, ResourceUtil.resourceFile("dbmodeler/POSTGRES-indexes.json"));
        Mockito.when(dbSchemaReader.indexesData(Mockito.any(), Mockito.any())).thenReturn(table);

        table = new Table();
        DataTableUtil.loadDataJsonFile(table, ResourceUtil.resourceFile("dbmodeler/POSTGRES-sequences.json"));
        Mockito.when(dbSchemaReader.sequencesData(Mockito.any(), Mockito.any())).thenReturn(table);

        setDbSchemaReader(dbSchemaReader);
    }

    @Test
    void readSchemaTest() throws SQLException, IOException, FrameworkException {
        super.readSchemaTest(DataBaseType.POSTGRES);
    }

}
