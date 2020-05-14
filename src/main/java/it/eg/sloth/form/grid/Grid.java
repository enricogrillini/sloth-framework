package it.eg.sloth.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Enrico Grillini
 */
@Getter
@Setter
public class Grid<D extends DataTable<? extends DataRow>> extends Fields<D> {

    String title;

    boolean backButtonHidden;
    boolean selectButtonHidden;

    boolean firstButtonHidden;
    boolean prevPageButtonHidden;
    boolean prevButtonHidden;
    boolean detailButtonHidden;
    boolean nextButtonHidden;
    boolean nextPageButtonHidden;
    boolean lastButtonHidden;
    boolean insertButtonHidden;
    boolean deleteButtonHidden;
    boolean updateButtonHidden;
    boolean commitButtonHidden;
    boolean rollbackButtonHidden;

    public Grid(String name) {
        this(name, null, null, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
    }

    public Grid(String name, String description, String title, Boolean backButtonHidden, Boolean selectPageButtonHidden, Boolean firstButtonHidden, Boolean prevPageButtonHidden,
                Boolean prevButtonHidden, Boolean detailButtonHidden, Boolean nextButtonHidden, Boolean nextPageButtonHidden, Boolean lastPageButtonHidden, Boolean insertButtonHidden,
                Boolean deleteButtonHidden, Boolean updateButtonHidden, Boolean commitButtonHidden, Boolean rollbackButtonHidden) {
        super(name, description);

        this.title = title;
        this.backButtonHidden = backButtonHidden != null && backButtonHidden;
        this.selectButtonHidden = selectPageButtonHidden != null && selectPageButtonHidden;
        this.firstButtonHidden = firstButtonHidden != null && firstButtonHidden;
        this.prevPageButtonHidden = prevPageButtonHidden != null && prevPageButtonHidden;
        this.prevButtonHidden = prevButtonHidden != null && prevButtonHidden;
        this.detailButtonHidden = detailButtonHidden != null && detailButtonHidden;
        this.nextButtonHidden = nextButtonHidden != null && nextButtonHidden;
        this.nextPageButtonHidden = nextPageButtonHidden != null && nextPageButtonHidden;
        this.lastButtonHidden = lastPageButtonHidden != null && lastPageButtonHidden;
        this.insertButtonHidden = insertButtonHidden != null && insertButtonHidden;
        this.deleteButtonHidden = deleteButtonHidden != null && deleteButtonHidden;
        this.updateButtonHidden = updateButtonHidden != null && updateButtonHidden;
        this.commitButtonHidden = commitButtonHidden != null && commitButtonHidden;
        this.rollbackButtonHidden = rollbackButtonHidden != null && rollbackButtonHidden;
    }

    public boolean isEmpty() {
        return getDataSource() == null || getDataSource().size() == 1;
    }

    /**
     * Imposta il contenuto della griglia prelevandolo dal DataTable associato
     */
    @Override
    public void copyFromDataSource() throws BusinessException {
        if (getDataSource() != null) {
            copyFromDataSource(getDataSource());
        } else {
            clearData();
        }
    }

    /**
     * Ricopia il contenuto della griglia sulla DataTable associata
     */
    @Override
    public void copyToDataSource() {
        if (getDataSource() != null) {
            copyToDataSource(getDataSource());
        }
    }

    public int size() {
        if (getDataSource() != null) {
            return getDataSource().size();
        } else {
            return -1;
        }
    }

    @Override
    public boolean validate(MessageList messages) throws BusinessException {
        if (size() == 0) {
            return true;
        }

        return super.validate(messages);
    }

    public boolean hasTotalizer() {
        for (SimpleField element : this) {
            if (element instanceof TextTotalizer || element instanceof InputTotalizer) {
                return true;
            }
        }

        return false;
    }

}
