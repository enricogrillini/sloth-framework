package it.eg.sloth.dbmodeler.model.connection;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DbConnection {

  String name;
  String jdbcUrl;
  String dbUser;
  String dbPassword;
  String dbOwner;
  DataBaseType dataBaseType;

}
