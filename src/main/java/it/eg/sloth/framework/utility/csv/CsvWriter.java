package it.eg.sloth.framework.utility.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
@Getter
@Setter
public class CsvWriter {
    public static final String DEFAULT_SEPARATOR = ",";
    public static final String DEFAULT_DELIMITATOR = "\"";

    private String separator;
    private String delimitator;
    private boolean writeHeader;

    public CsvWriter() {
        setSeparator(DEFAULT_SEPARATOR);
        setDelimitator(DEFAULT_DELIMITATOR);
        setWriteHeader(true);
    }

    /**
     * Formatta un valore da scrivere nel csv
     *
     * @param value
     * @return
     */
    private String formatValue(String value) {
        if (!BaseFunction.isBlank(getDelimitator())) {
            value = value.replace(getDelimitator(), getDelimitator() + getDelimitator());
        } else {
            value = value.replace(getSeparator(), "");
        }

        // Elimino i fine linea
        value = value.replace("\n", "");
        value = value.replace("\r", "");

        // Aggiungo il delimitatore di stringa
        value = getDelimitator() + value + getDelimitator();

        return value;
    }

    /**
     * Scrive l'intestazione
     *
     * @param outputStream
     * @throws IOException
     */
    private void writeHeader(Grid<?> grid, OutputStream outputStream) throws IOException {
        for (SimpleField simpleField : grid) {
            if (simpleField instanceof DataField) {
                String value = formatValue(simpleField.getDescription());

                // Scrivo il valore
                outputStream.write(value.getBytes());

                // Scrivo il separatore
                outputStream.write(getSeparator().getBytes());
            }
        }

        outputStream.write("\r\n".getBytes());
    }

    /**
     * Scrive il body
     *
     * @param outputStream
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    private void writeBody(Grid<?> grid, OutputStream outputStream) throws IOException, CloneNotSupportedException, FrameworkException {
        for (DataRow row : grid.getDataSource()) {

            for (SimpleField simpleField : grid) {
                if (simpleField instanceof DataField) {
                    DataField<?> field = (DataField<?>) simpleField.newInstance();
                    field.copyFromDataSource(row);

                    String value = formatValue(field.getData());

                    // Scrivo il valore
                    outputStream.write(value.getBytes());

                    // Scrivo il separatore
                    outputStream.write(getSeparator().getBytes());
                }
            }

            outputStream.write("\r\n".getBytes());
        }
    }

    /**
     * Scrive la griglia in formato csv
     *
     * @param grid
     * @param outputStream
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void write(Grid<?> grid, OutputStream outputStream) throws IOException, CloneNotSupportedException, FrameworkException {
        if (isWriteHeader())
            writeHeader(grid, outputStream);
        writeBody(grid, outputStream);
    }

    /**
     * Scrive la griglia in formato csv
     *
     * @param grid
     * @return
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public byte[] write(Grid<?> grid) throws IOException, CloneNotSupportedException, FrameworkException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        write(grid, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public void setSeparator(String separator) {
        if (BaseFunction.isBlank(separator)) {
            this.separator = DEFAULT_SEPARATOR;
        } else {
            this.separator = separator;
        }
    }

}
