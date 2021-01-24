package it.eg.sloth.dbmodeler.reader.oracle;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.reader.DbSchemaAbstractReader;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OracleSchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {



    private static final String SQL_DB_SEQUENCES = "Select InitCap(sequence_name) sequence_name\n" +
            "From ###sequences\n" +
            "Where sequence_owner = upper(?)\n" +
            "Order By sequence_name";


    private boolean selectAnyDictionary;

    public OracleSchemaReader(DbConnection dbConnection) {
        super(dbConnection);
        this.selectAnyDictionary = false;  // Al momento è gestita la casistica più restrittiva
    }

    private String getSqlStatement(String sql) {
        if (selectAnyDictionary) {
            return sql.replace("###", "DBA_");
        } else {
            return sql.replace("###", "ALL_");
        }
    }

    @Override
    public List<Sequence>  loadSequences() throws SQLException, IOException, FrameworkException {
        // Sequences
        it.eg.sloth.db.datasource.table.Table sqlDbSequences = new it.eg.sloth.db.datasource.table.Table();

        Query query = new Query(getSqlStatement(SQL_DB_SEQUENCES));
        query.addParameter(Types.VARCHAR, getDbConnection().getDbOwner());
        query.populateDataTable(sqlDbSequences, getConnection());

        // Lettura sequence
        List<Sequence> sequenceList = new ArrayList<>();
        for (Row row : sqlDbSequences) {
            Sequence sequence = Sequence.builder()
                    .name(row.getString("sequence_name"))
                    .build();

            sequenceList.add(sequence);
        }

        return sequenceList;
    }
}
