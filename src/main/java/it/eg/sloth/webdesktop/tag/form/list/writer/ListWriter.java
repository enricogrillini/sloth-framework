package it.eg.sloth.webdesktop.tag.form.list.writer;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.webdesktop.tag.form.AbstractHtmlWriter;
import it.eg.sloth.webdesktop.tag.form.field.writer.TextControlWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe base per la stampa di una lista
 *
 * @author Enrico Grillini
 */
@Slf4j
public class ListWriter extends AbstractHtmlWriter {

    public static String writeTitle(Grid<?> grid) {
        if (BaseFunction.isBlank(grid.getTitle())) {
            return StringUtil.EMPTY;
        } else {
            return new StringBuilder()
                    .append(getElement("h1", grid.getTitle()))
                    .append(getElement("br"))
                    .toString();
        }
    }


    public static String writeList(Grid<?> grid) throws BusinessException, CloneNotSupportedException {
        StringBuilder result = new StringBuilder()
                .append("<div class=\"list-group list-group-flush\">\n");

        for (DataRow dataRow : grid.getDataSource()) {
            for (SimpleField field : grid.getElements()) {
                SimpleField appField = (SimpleField) field.clone();

                if (appField instanceof DataField) {
                    DataField<?> dataField = (DataField<?>) appField;
                    dataField.copyFromDataSource(dataRow);

                    result
                            .append(" <span class=\"list-group-item\">")
                            .append(TextControlWriter.writeControl(appField))
                            .append("</span>\n");

                    // NOTA - Stampo solo il primo campo
                    break;
                }
            }
        }

        return result
                .append("</div>\n")
                .toString();
    }

}
