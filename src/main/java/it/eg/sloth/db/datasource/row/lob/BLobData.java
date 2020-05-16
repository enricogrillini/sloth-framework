package it.eg.sloth.db.datasource.row.lob;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.Blob;

public class BLobData extends LobData<byte[]> {

    public BLobData() {
        super();
    }

    public BLobData(boolean load, Blob blob) {
        this();

        if (load && blob != null) {
            try (InputStream inputStream = blob.getBinaryStream()) {
                value = IOUtils.toByteArray(inputStream);
                setStatus(LobData.ON_LINE);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        setValue((byte[]) in.readObject());
    }

}
