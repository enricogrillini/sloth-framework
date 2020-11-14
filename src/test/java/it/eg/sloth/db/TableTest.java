package it.eg.sloth.db;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.model.ProvaTableBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Slf4j
public class TableTest {

    private static final String TO_STRING = "TableAbstract(sortingRules=SortingRules(list=[]), rows=[Row{values={key1=value1}}, Row{values={key2=value2}}], currentRow=1, pageSize=-1)";

    @Test
    public void tableToStringTest() {
        Table table = new Table();
        Row row = table.add();
        row.setString("key1", "value1");

        row = table.add();
        row.setString("key2", "value2");

        assertEquals(TO_STRING, table.toString());
    }

}
