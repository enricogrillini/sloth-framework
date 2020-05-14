package it.eg.sloth.webdesktop.tag.form.list;

import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.webdesktop.tag.form.base.BaseElementTag;
import it.eg.sloth.webdesktop.tag.form.list.writer.ListWriter;

/**
 * Tag per la stampa di un lista
 *
 * @author Enrico Grillini
 */
public class ListTag<T extends Grid<?>> extends BaseElementTag<T> {

    @Override
    protected int startTag() throws Throwable {
        Grid<?> grid = getElement();

        // Write Title
        writeln(ListWriter.writeTitle(grid));
        writeln(ListWriter.writeList(grid));

        return SKIP_BODY;
    }

    @Override
    protected void endTag() throws Throwable {
        // NOP
    }

}
