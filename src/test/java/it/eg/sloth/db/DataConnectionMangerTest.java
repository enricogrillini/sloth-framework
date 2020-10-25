package it.eg.sloth.db;

import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.RowStatus;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.db.model.ProvaRowBean;
import it.eg.sloth.db.model.ProvaTableBean;
import it.eg.sloth.db.query.filteredquery.FilteredQuery;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.framework.common.base.TimeStampUtil;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataConnectionMangerTest {

    private static final String FILTERED_QUERY_TO_STRING = "\n" +
            "Statement: Select * from Prova /*W*/\n" +
            "SqlStatement: Select * from Prova Where Id = ?\n" +
            "Filter {\"sql\":\"Id = ?\",\"sqlTypes\":4,\"value\":2,\"whereCondition\":\"Id = ?\"}";

    private static final String QUERY_TO_STRING = "\n" +
            "Statement: Select * from Prova where Id = ? order by 1\n" +
            "SqlStatement: Select * from Prova where Id = ? order by 1\n" +
            "Parameter {\"sqlType\":4,\"value\":1}";

    private static final String TABLE_BEAN_TO_STRING = "TableAbstract(sortingRules=SortingRules(list=[]), rows=[Row{values={id=2, testo=BbbbbX, data=2020-01-01 00:00:00.0}}], currentRow=0, pageSize=-1)";


    private static final String driverClassName = "org.h2.Driver";
    private static final String url = "jdbc:h2:mem:inputDb;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:db/create.sql'";
    private static final String userName = "gilda";
    private static final String password = "gilda";

    @BeforeClass
    public static void init() {
        DataConnectionManager.getInstance().registerDefaultDataSource(driverClassName, url, userName, password, 1, 100, 1);
    }

    /**
     * Empty Table
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void ds0EmptyTest() throws SQLException, IOException, FrameworkException {
        Table dataTable = new Table();

        assertEquals(0, dataTable.values().size());
        assertEquals(0, dataTable.keys().size());
    }


    /**
     * Lettura di un generico DataSource
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void ds1QuerySelectTest() throws SQLException, IOException, FrameworkException {
        Query query = new Query("Select * from Prova order by 1");
        DataTable dataTable = query.selectTable();

        assertEquals(2, dataTable.size());
        assertEquals(BigDecimal.valueOf(1), dataTable.getBigDecimal("Id"));
        assertEquals("Aaaaa", dataTable.getString("Testo"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), dataTable.getTimestamp("Data"));

        assertEquals(3, dataTable.getRow().values().size());
        assertEquals(3, dataTable.getRow().keys().size());

        assertEquals(3, dataTable.values().size());
        assertEquals(3, dataTable.keys().size());
    }

    @Test
    public void ds2QueryExecuteTest() throws SQLException, IOException, FrameworkException {
        Query query = new Query("Update Prova set testo = testo || 'X'");
        query.execute();

        query = new Query("Select * from Prova order by 1");
        DataTable dataTable = query.selectTable();

        assertEquals(2, dataTable.size());
        assertEquals(BigDecimal.valueOf(1), dataTable.getBigDecimal("Id"));
        assertEquals("AaaaaX", dataTable.getString("Testo"));
        assertEquals(TimeStampUtil.parseTimestamp("01/01/2020", "dd/MM/yyyy"), dataTable.getTimestamp("Data"));
    }

    /**
     * Test Filtered Query
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void ds3FilteredQueryTest() throws SQLException, IOException, FrameworkException {
        // Simple Filter
        FilteredQuery query = new FilteredQuery("Select * from Prova /*W*/");
        query.addFilter("Id = ?", Types.INTEGER, 2);

        ProvaTableBean provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(1, provaTableBean.size());
        assertEquals(BigDecimal.valueOf(2), provaTableBean.getRow().getId());

        ProvaRowBean provaRowBean = ProvaRowBean.Factory.load(query);
        assertEquals(BigDecimal.valueOf(2), provaRowBean.getId());

        // Text Search
        query = new FilteredQuery("Select * from Prova /*W*/");
        query.addTextSearch("upper(testo) like '%' || upper(?) || '%'", "aaa X");

        provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(1, provaTableBean.size());
        assertEquals(BigDecimal.valueOf(1), provaTableBean.getRow().getId());
    }


    /**
     * Lettura da TableBeans
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void ds4LoadTableBeanTest() throws SQLException, IOException, FrameworkException {
        ProvaTableBean provaTableBean = ProvaTableBean.Factory.load(null);

        assertEquals(2, provaTableBean.size());
        assertEquals(BigDecimal.valueOf(1), provaTableBean.getBigDecimal("Id"));
        assertEquals("AaaaaX", provaTableBean.getString("Testo"));
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
    public void ds5WriteTableBeanTest() throws SQLException, IOException, FrameworkException {
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


    /**
     * Test Rowbean
     *
     * @throws SQLException
     * @throws IOException
     * @throws FrameworkException
     */
    @Test
    public void ds6RowBeanTest() throws SQLException, IOException, FrameworkException {
        Query query = new Query("Select * from Prova where id = 4");

        ProvaRowBean provaRowBean = new ProvaRowBean();
        provaRowBean.setId(BigDecimal.valueOf(4));
        provaRowBean.setTesto("Ddddd");
        provaRowBean.setData(TimeStampUtil.parseTimestamp("01/03/2020", "dd/MM/yyyy"));

        ProvaTableBean provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(0, provaTableBean.size());

        // Insert
        assertEquals(RowStatus.INSERTED, provaRowBean.getStatus());
        provaRowBean.save();
        assertEquals(RowStatus.CLEAN, provaRowBean.getStatus());

        provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(1, provaTableBean.size());

        // Verifica Insert
        ProvaRowBean provaRowBean2 = new ProvaRowBean();
        provaRowBean2.setId(BigDecimal.valueOf(4));
        provaRowBean2.select();

        assertEquals(RowStatus.CLEAN, provaRowBean2.getStatus());
        assertEquals(BigDecimal.valueOf(4), provaRowBean2.getId());
        assertEquals("Ddddd", provaRowBean2.getTesto());
        assertEquals(TimeStampUtil.parseTimestamp("01/03/2020", "dd/MM/yyyy"), provaRowBean2.getData());

        // Update
        provaRowBean.setTesto("prova");
        assertEquals(RowStatus.UPDATED, provaRowBean.getStatus());

        provaRowBean.save();
        assertEquals(RowStatus.CLEAN, provaRowBean.getStatus());

        // Verifica Update
        provaRowBean2 = new ProvaRowBean();
        provaRowBean2.setId(BigDecimal.valueOf(4));
        provaRowBean2.select();
        assertEquals("prova", provaRowBean2.getTesto());

        // Delete
        provaRowBean.remove();
        assertEquals(RowStatus.DELETED, provaRowBean.getStatus());

        provaRowBean.save();
        assertEquals(RowStatus.INCONSISTENT, provaRowBean.getStatus());

        // Verifica Delete
        provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(0, provaTableBean.size());
    }

    @Test
    public void ds7TableBeanExceptionTest() throws SQLException, IOException, FrameworkException {

        ProvaTableBean provaTableBean = new ProvaTableBean();

        ProvaRowBean provaRowBean = provaTableBean.add();
        provaRowBean.setId(BigDecimal.valueOf(3));
        provaRowBean.setTesto("Ccccc");
        provaRowBean.setData(TimeStampUtil.parseTimestamp("01/02/2020", "dd/MM/yyyy"));

        SQLException sqlException = assertThrows(SQLException.class, () -> {
            provaTableBean.save();
        });


        provaRowBean.setId(BigDecimal.valueOf(5));
        provaTableBean.save();
    }


    @Test
    public void queryToStringTest() throws SQLException, IOException, FrameworkException {
        // FilteredQuery
        FilteredQuery filteredQuery = new FilteredQuery("Select * from Prova /*W*/");
        filteredQuery.addFilter("Id = ?", Types.INTEGER, 2);

        assertEquals(FILTERED_QUERY_TO_STRING, filteredQuery.toString());

        // Query
        Query query = new Query("Select * from Prova where Id = ? order by 1");
        query.addParameter(Types.INTEGER, BigDecimal.valueOf(1));

        assertEquals(QUERY_TO_STRING, query.toString());
    }

    @Test
    public void tableToStringTest() throws SQLException, IOException, FrameworkException {
        // Simple Filter
        FilteredQuery query = new FilteredQuery("Select * from Prova /*W*/");
        query.addFilter("Id = ?", Types.INTEGER, 2);

        ProvaTableBean provaTableBean = ProvaTableBean.Factory.loadFromQuery(query);
        assertEquals(TABLE_BEAN_TO_STRING, provaTableBean.toString());
    }

}
