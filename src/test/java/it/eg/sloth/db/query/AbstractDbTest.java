package it.eg.sloth.db.query;

import it.eg.sloth.db.manager.DataConnectionManager;
import it.eg.sloth.framework.common.exception.FrameworkException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.SQLException;


public abstract class AbstractDbTest {

    private static final String driverClassName = "org.h2.Driver";
    private static final String url = "jdbc:h2:mem:inputDb;MODE=Oracle;INIT=RUNSCRIPT FROM 'classpath:db/create.sql'";
    private static final String userName = "gilda";
    private static final String password = "gilda";

    @BeforeAll
    public static void init() {
        DataConnectionManager.getInstance().registerDefaultDataSource(driverClassName, url, userName, password, 1, 100, 1);
    }

    @AfterAll
    public static void end() throws FrameworkException, SQLException {
        DataConnectionManager.getInstance().getDataSource().getConnection().createStatement().execute("SHUTDOWN");
    }

}
