package it.eg.sloth.db.manager;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataConnectionMangerTest {
    private static final String driverClassName = "org.h2.Driver";
    private static final String url = "jdbc:h2:mem:inputDb;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:db/create.sql'";
    private static final String userName = "gilda";
    private static final String password = "gilda";

    @BeforeClass
    public static void init() {
        DataConnectionManager.getInstance().registerDefaultDataSource(driverClassName, url, userName, password, 1, 100, 1);
    }

    /**
     * Lettura di un generico DataSource
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void datasource1Test() throws SQLException, IOException, FrameworkException {
        Query query = new Query("Select * from Prova order by 1");
        DataTable dataTable = query.selectTable();

        assertEquals(2, dataTable.size());
        assertEquals(BigDecimal.valueOf(1), dataTable.getBigDecimal("Id"));
        assertEquals("Aaaaa", dataTable.getString("Testo"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), dataTable.getTimestamp("Data"));
    }

    /**
     * Lettura da TableBeans
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void datasource2Test() throws SQLException, IOException, FrameworkException {
        ProvaTableBean provaTableBean = ProvaTableBean.Factory.load(null);

        assertEquals(2, provaTableBean.size());
        assertEquals(BigDecimal.valueOf(1), provaTableBean.getBigDecimal("Id"));
        assertEquals("Aaaaa", provaTableBean.getString("Testo"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), provaTableBean.getTimestamp("Data"));
    }

    /**
     * Scrittura con tableBean
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void datasource3Test() throws SQLException, IOException, FrameworkException {
        ProvaTableBean provaTableBean = new ProvaTableBean();

        ProvaRowBean provaRowBean = provaTableBean.add();
        provaRowBean.setId(BigDecimal.valueOf(3));
        provaRowBean.setTesto("Ccccc");
        provaRowBean.setData(TimeStampUtil.parseTimestamp("01/02/2020", "dd/MM/yyyy"));

        provaTableBean.save();

        // Rileggo la tabella
        ProvaTableBean provaTableBean2 = ProvaTableBean.Factory.load(null);
        provaTableBean2.addSortingRule(ProvaRowBean.ID, SortingRule.SORT_DESC_NULLS_LAST);
        provaTableBean2.applySort();

        // Verifico che esista il record inserito corettamente
        ProvaRowBean provaRowBean2 = provaTableBean.getRow();
        assertEquals(3, provaTableBean2.size());
        assertEquals(BigDecimal.valueOf(3), provaRowBean2.getId());
        assertEquals("Ccccc", provaRowBean2.getTesto());
        assertEquals(TimeStampUtil.parseTimestamp("01/02/2020", "dd/MM/yyyy"), provaRowBean2.getData());
    }

}
