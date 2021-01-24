package it.eg.sloth.dbmodeler.reader.h2;

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
import java.util.Collection;
import java.util.List;

public class H2SchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String SQL_DB_SEQUENCES = "Select sequence_name from INFORMATION_SCHEMA.SEQUENCES Where sequence_Schema = ? Order by sequence_name";

    public H2SchemaReader(DbConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    public Collection<Sequence> loadSequences() throws SQLException, IOException, FrameworkException {
        // Sequences
        it.eg.sloth.db.datasource.table.Table sqlDbSequences = new it.eg.sloth.db.datasource.table.Table();

        Query query = new Query(SQL_DB_SEQUENCES);
        query.addParameter(Types.VARCHAR, getDbConnection().getDbOwner());
        query.populateDataTable(sqlDbSequences, getConnection());

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
