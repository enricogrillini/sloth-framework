package it.eg.sloth.dbmodeler.reader.postgres;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.query.query.Query;
import it.eg.sloth.dbmodeler.model.connection.DbConnection;
import it.eg.sloth.dbmodeler.model.schema.sequence.Sequence;
import it.eg.sloth.dbmodeler.reader.DbSchemaAbstractReader;
import it.eg.sloth.dbmodeler.reader.DbSchemaReader;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class PostgresSchemaReader extends DbSchemaAbstractReader implements DbSchemaReader {

    private static final String SQL_DB_SEQUENCES = "SELECT c.relname sequence_name FROM pg_class c WHERE c.relkind = 'S' order by 1";

    public PostgresSchemaReader(DbConnection dbConnection) {
        super(dbConnection);
    }


    @Override
    public Collection<Sequence> loadSequences() throws SQLException, IOException, FrameworkException {
        // Sequences
        it.eg.sloth.db.datasource.table.Table sqlDbSequences = new it.eg.sloth.db.datasource.table.Table();

        Query query = new Query(SQL_DB_SEQUENCES);
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
