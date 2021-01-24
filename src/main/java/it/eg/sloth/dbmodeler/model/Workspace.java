package it.eg.sloth.dbmodeler.model;

import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class Workspace {

    BigDecimal idWorkspace;
    String name;
    List<DbConnection> dbConnectionList;
    Schema schema;

    String activeDbConnection;

    /**
     * Imposta la connessione attiva
     *
     * @param dbConnectionName
     */
    public void setDbActiveConnection(String dbConnectionName) {
        if (dbConnectionList.isEmpty()) {
            activeDbConnection = null;
        } else {
            for (DbConnection dbConnection : dbConnectionList) {
                if (dbConnection.getName().equalsIgnoreCase(dbConnectionName)) {
                    activeDbConnection = dbConnection.getName();
                    return;
                }
            }

            activeDbConnection = dbConnectionList.get(0).getName();
        }
    }

}
