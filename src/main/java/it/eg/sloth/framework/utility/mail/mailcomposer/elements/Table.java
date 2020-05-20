package it.eg.sloth.framework.utility.mail.mailcomposer.elements;

/**
 * @author Enrico Grillini
 */
public class Table {

    private boolean[] border;

    public Table() {
        this(new boolean[]{true, false, false, true});
    }

    public Table(boolean[] border) {
        this.border = border;
    }

    public boolean[] getBorder() {
        return border;
    }

    public void setBorder(boolean[] border) {
        this.border = border;
    }

}
