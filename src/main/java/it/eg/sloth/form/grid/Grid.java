package it.eg.sloth.form.grid;

import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.form.fields.Fields;
import it.eg.sloth.form.fields.field.SimpleField;
import it.eg.sloth.form.fields.field.base.TextField;
import it.eg.sloth.form.fields.field.impl.InputTotalizer;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.MessageList;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
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
    public void copyFromDataSource() throws FrameworkException {
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
    public void copyToDataSource() throws FrameworkException {
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
    public boolean validate(MessageList messages) throws FrameworkException {
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

    public void orderBy(String fieldName, int sortType) {
        if (getElement(fieldName) instanceof TextField) {
            TextField<?> textField = (TextField<?>) getElement(fieldName);

            DataTable<?> dataTable = getDataSource();
            dataTable.clearSortingRules();
            dataTable.addSortingRule(textField.getOrderByAlias(), sortType);
            dataTable.applySort(false);
        }
    }

    public Grid<D> newInstance() {
        Grid<D> result = new Grid<>(getName());
        for (SimpleField field : this.getElements()) {
            result.addChild(field.newInstance());
        }

        return result;
    }

}
