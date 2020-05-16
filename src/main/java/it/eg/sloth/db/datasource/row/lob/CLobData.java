package it.eg.sloth.db.datasource.row.lob;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.sql.SQLException;

public class CLobData extends LobData<String> {

    public CLobData() {
        super();
    }

    public CLobData(boolean load, Clob clob) {
        this();

        if (load && clob != null) {
            try (InputStream inputStream = clob.getAsciiStream()) {
                value = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
                setStatus(LobData.ON_LINE);
            } catch (IOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeObject(getValue());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        setValue((String) in.readObject());
    }

}
