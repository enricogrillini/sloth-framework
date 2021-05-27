package it.eg.sloth.dbmodeler.reader;

import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbSchemaAbstractReader {

    public static final int LOGIN_TIMEOUT = 5;

    @Getter
    @Setter
    private DbConnection dbConnection;

    @Getter
    @Setter
    private Connection connection;

    public DbSchemaAbstractReader(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Connection getJdbcConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return connection;
        }

        try {
            DriverManager.setLoginTimeout(LOGIN_TIMEOUT);
            this.connection = DriverManager.getConnection(getDbConnection().getJdbcUrl(), getDbConnection().getDbUser(), getDbConnection().getDbPassword());
        } catch (Exception e) {
            this.connection = null;
        }

        return this.connection;
    }


}
