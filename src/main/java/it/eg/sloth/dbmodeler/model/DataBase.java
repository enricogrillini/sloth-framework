package it.eg.sloth.dbmodeler.model;

import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.Schema;
import lombok.Data;

@Data
public class DataBase {

    DbConnection dbConnection;
    Schema schema;

}
