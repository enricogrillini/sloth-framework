package it.eg.sloth.dbmodeler.model.connection;

import it.eg.sloth.dbmodeler.model.database.DataBaseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DbConnection {

  String name;
  String jdbcUrl;
  String dbUser;
  String dbPassword;
  String dbOwner;
  DataBaseType dataBaseType;

}
