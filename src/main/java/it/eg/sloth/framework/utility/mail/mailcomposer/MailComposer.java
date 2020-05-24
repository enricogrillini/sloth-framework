package it.eg.sloth.framework.utility.mail.mailcomposer;

import it.eg.sloth.framework.FrameComponent;
import it.eg.sloth.framework.utility.mail.mailcomposer.elements.*;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enrico Grillini
 */
public abstract class MailComposer extends FrameComponent {


    private String title;

    private List<Object> elements = new ArrayList<>();
    private boolean lineIsOpen = false;
    private boolean bulletListIsOpen = false;
    private boolean bulletIsOpen = false;
    private boolean tableIsOpen = false;
    private boolean tableRowIsOpen = false;
    private boolean tableCellIsOpen = false;

    public MailComposer(String title) {
        this.title = title;
        elements = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Line addLine(String text) {
        Line line = new Line(text);
        elements.add(line);

        return line;
    }

    public Line addBlankLine() {
        Line line = new Line("&nbsp;");
        elements.add(line);

        return line;
    }

    public BulletList addBulletList() {
        BulletList result = new BulletList();
        elements.add(result);

        return result;
    }

    public Bullet addBullet(String text) {
        Bullet result = new Bullet(text);
        elements.add(result);

        return result;
    }

    public Table addTable() {
        Table result = new Table();
        elements.add(result);

        return result;
    }

    public Table addTable(boolean[] border) {
        Table result = new Table(border);
        elements.add(result);

        return result;
    }

    public TableRow addTableRow() {
        TableRow result = new TableRow();
        elements.add(result);

        return result;
    }

    public TableCell addTableCell(String text) {
        TableCell result = new TableCell(text);
        elements.add(result);

        return result;
    }

    public TableCell addTableCell(String text, boolean alignRight) {
        TableCell result = new TableCell(text, alignRight);
        elements.add(result);

        return result;
    }

    public TableCell addTableCell(int colspan, String text) {
        TableCell result = new TableCell(colspan, text);
        elements.add(result);

        return result;
    }

    public TableCell addTableCell(int colspan, String text, boolean alignRight) {
        TableCell result = new TableCell(colspan, text, alignRight);
        elements.add(result);

        return result;
    }

    public TableCell addTableCell(int colspan, String text, boolean[] border) {
        TableCell result = new TableCell(colspan, text, border, false);
        elements.add(result);

        return result;
    }

    public EmbeddedImage addEmbeddedImage(byte[] image, String name, String type, String link) {
        EmbeddedImage result = new EmbeddedImage(image, name, type, link);
        elements.add(result);

        return result;
    }

    public EmbeddedImage addEmbeddedImage(File image, String name, String type, String link) throws IOException {
        EmbeddedImage result = new EmbeddedImage(image, name, type, link);
        elements.add(result);

        return result;
    }

    private void initContainer() {
        lineIsOpen = false;

        bulletListIsOpen = false;
        bulletIsOpen = false;

        tableIsOpen = false;
        tableRowIsOpen = false;
        tableCellIsOpen = false;
    }

    protected void writeOpenMail(StringBuilder buffer) {
        openMail(buffer);
    }

    protected void writeCloseMail(StringBuilder buffer) {
        closeMail(buffer);
    }

    protected void writeOpenLine(StringBuilder buffer) {
        if (!lineIsOpen)
            openLine(buffer);

        lineIsOpen = true;
    }

    protected void writeCloseLine(StringBuilder buffer) {
        if (lineIsOpen)
            closeLine(buffer);

        lineIsOpen = false;
    }

    protected void writeOpenBulletList(StringBuilder buffer) {
        if (!bulletListIsOpen)
            openBulletList(buffer);

        bulletListIsOpen = true;
    }

    protected void writeCloseBulletList(StringBuilder buffer) {
        if (bulletListIsOpen)
            closeBulletList(buffer);

        bulletListIsOpen = false;
    }

    protected void writeOpenBullet(StringBuilder buffer) {
        if (!bulletIsOpen)
            openBullet(buffer);

        bulletIsOpen = true;
    }

    protected void writeCloseBullet(StringBuilder buffer) {
        if (bulletIsOpen)
            closeBullet(buffer);

        bulletIsOpen = false;
    }

    protected void writeOpenTable(Table table, StringBuilder buffer) {
        if (!tableIsOpen)
            openTable(table, buffer);

        tableIsOpen = true;
    }

    protected void writeCloseTable(StringBuilder buffer) {
        if (tableIsOpen)
            closeTable(buffer);
        tableIsOpen = false;
    }

    protected void writeOpenTableRow(StringBuilder buffer) {
        if (!tableRowIsOpen)
            openTableRow(buffer);

        tableRowIsOpen = true;
    }

    protected void writeCloseTableRow(StringBuilder buffer) {
        if (tableRowIsOpen)
            closeTableRow(buffer);

        tableRowIsOpen = false;
    }

    protected void writeOpenTableCell(TableCell tableCell, StringBuilder buffer) {
        openTableCell(tableCell, buffer);
    }

    protected void writeCloseTableCell(StringBuilder buffer) {
        if (tableCellIsOpen)
            closeTableCell(buffer);

        tableCellIsOpen = false;
    }

    protected abstract void openMail(StringBuilder buffer);

    protected abstract void closeMail(StringBuilder buffer);

    protected abstract void openLine(StringBuilder buffer);

    protected abstract void closeLine(StringBuilder buffer);

    protected abstract void openBulletList(StringBuilder buffer);

    protected abstract void closeBulletList(StringBuilder buffer);

    protected abstract void openBullet(StringBuilder buffer);

    protected abstract void closeBullet(StringBuilder buffer);

    protected abstract void openTable(Table table, StringBuilder buffer);

    protected abstract void closeTable(StringBuilder buffer);

    protected abstract void openTableRow(StringBuilder buffer);

    protected abstract void closeTableRow(StringBuilder buffer);

    protected abstract void openTableCell(TableCell tableCell, StringBuilder buffer);

    protected abstract void closeTableCell(StringBuilder buffer);

    protected abstract void writeEmbededImage(EmbeddedImage embeddedImage, StringBuilder buffer, HtmlEmail htmlEmail) throws IOException, EmailException;

    private void writeCloseAll(StringBuilder builder) {
        writeCloseAll(builder, true, true, true);
    }

    private void writeCloseAll(StringBuilder builder, boolean bulletList, boolean tableRow, boolean table) {

        // Line
        writeCloseLine(builder);

        // Bullet List
        writeCloseBullet(builder);
        if (bulletList) {
            writeCloseBulletList(builder);
        }

        // Table
        writeCloseTableCell(builder);
        if (tableRow) {
            writeCloseTableRow(builder);
        }

        if (table) {
            writeCloseTable(builder);
        }
    }

    public void writeMessage(HtmlEmail htmlEmail) throws EmailException, IOException {
        StringBuilder builder = new StringBuilder();

        writeOpenMail(builder);

        initContainer();

        for (Object object : elements) {
            if (object instanceof Line) {
                // Line
                writeCloseAll(builder);

                Line line = (Line) object;
                writeOpenLine(builder);
                builder.append(line.getText());

            } else if (object instanceof BulletList) {
                // BulletList
                writeCloseAll(builder);
                writeOpenBulletList(builder);

            } else if (object instanceof Bullet) {
                // Bullet
                writeCloseAll(builder, false, true, true);

                Bullet bullet = (Bullet) object;
                writeOpenBullet(builder);
                builder.append(bullet.getText());

            } else if (object instanceof Table) {
                // Table
                writeCloseAll(builder);

                Table table = (Table) object;
                writeOpenTable(table, builder);

            } else if (object instanceof TableRow) {
                // TableRow
                writeCloseAll(builder, true, true, false);
                writeOpenTableRow(builder);

            } else if (object instanceof TableCell) {
                // TableCell
                writeCloseAll(builder, true, false, false);

                TableCell tableCell = (TableCell) object;
                writeOpenTableCell(tableCell, builder);
                builder.append(tableCell.getText());

            } else if (object instanceof EmbeddedImage) {
                // Immagine
                EmbeddedImage embeddedImage = (EmbeddedImage) object;
                writeEmbededImage(embeddedImage, builder, htmlEmail);
            }
        }

        writeCloseAll(builder);
        writeCloseMail(builder);

        htmlEmail.setHtmlMsg(builder.toString());
    }

}
