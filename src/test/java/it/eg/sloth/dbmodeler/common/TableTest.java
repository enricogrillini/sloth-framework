package it.eg.sloth.dbmodeler.common;

import it.eg.sloth.dbmodeler.model.DataBase;
import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import it.eg.sloth.dbmodeler.model.schema.table.Table;
import it.eg.sloth.framework.utility.resource.ResourceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class TableTest {

    @Test
    void tableTest() throws IOException {
        DataBase dataBase = new DataBase();
        dataBase.readJson(ResourceUtil.resourceFile("dbmodeler/" + DataBaseType.ORACLE + "-db.json"));

        Table table = dataBase.getSchema().getTable("Sec_utenti");

        Assertions.assertFalse(table.getPrimaryKeyCollection().isEmpty());
        Assertions.assertEquals(1, table.getPrimaryKeyCollection().size());
        Assertions.assertEquals("Idutente", table.getPrimaryKeyCollection().iterator().next().getName());

        Assertions.assertFalse(table.getPlainColumnCollection().isEmpty());
        Assertions.assertEquals(8, table.getPlainColumnCollection().size());

        Assertions.assertTrue(table.getClobColumnCollection().isEmpty());

        Assertions.assertFalse(table.getBlobColumnCollection().isEmpty());
        Assertions.assertEquals(1, table.getBlobColumnCollection().size());
        Assertions.assertEquals("Foto", table.getBlobColumnCollection().iterator().next().getName());
    }
}
