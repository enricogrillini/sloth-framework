package it.eg.sloth.form.dwh.rollup;

import it.eg.sloth.db.datasource.DataNode;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.node.Node;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class Rollup extends AbstractElements<DataField<?>> {


    private String description;

    private DataTable<?> dataTable;
    private DataNode dataNode;

    public Rollup(String name) {
        this(name, null);
    }

    public Rollup(String name, String description) {
        super(name);
        this.description = description;
        dataTable = null;
        dataNode = null;
    }

    public String getDescription() {
        return description;
    }

    public String getHtmlDescription() {
        return Casting.getHtml(getDescription());
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable<?> dataTable) throws FrameworkException {
        this.dataTable = dataTable;
        calculate();
    }

    public <T> List<Attribute<T>> getAttributes() {
        List<Attribute<T>> list = new ArrayList<>();

        for (DataField<?> element : this) {
            if (element instanceof Attribute) {
                list.add((Attribute<T>) element);
            }
        }

        return list;
    }

    public <T> List<Level<T>> getLevels() {
        List<Level<T>> list = new ArrayList<>();

        for (DataField<?> element : this) {
            if (element instanceof Level) {
                list.add((Level<T>) element);
            }
        }

        return list;
    }

    public <T> List<Measure<T>> getMeasures() {
        List<Measure<T>> list = new ArrayList<>();

        for (DataField<?> element : this) {
            if (element instanceof Measure) {
                list.add((Measure<T>) element);
            }
        }

        return list;
    }

    public DataNode getDataNode() {
        return dataNode;
    }

    private void calculate() throws FrameworkException {
        if (getDataTable() != null && getDataTable().size() > 0) {
            RollupCalculator calculator = new RollupCalculator(getDataTable());

            for (DataField<?> dataField : this) {
                if (dataField instanceof Level) {
                    calculator.addLevel(dataField.getName());
                } else if (dataField instanceof Measure) {
                    calculator.addMeasure(dataField.getName());
                } else if (dataField instanceof Attribute) {
                    calculator.addAttribute(dataField.getName());
                }
            }

            this.dataNode = calculator.recalc();
        } else {
            this.dataNode = new Node();
        }

    }

    private class RollupCalculator {

        private DataTable<?> dataTable;

        private Map<String, Level<?>> levelMap;
        private Map<String, Attribute<?>> attributeMap;
        private Map<String, Measure<?>> measureMap;

        public RollupCalculator(DataTable<?> dataTable) throws FrameworkException {
            setDataTable(dataTable);
            this.levelMap = new LinkedHashMap<>();
            this.attributeMap = new LinkedHashMap<>();
            this.measureMap = new LinkedHashMap<>();
        }

        public void setDataTable(DataTable<?> dataTable) throws FrameworkException {
            this.dataTable = new Table();

            for (DataRow row : dataTable) {
                DataRow newRow = this.dataTable.add();

                for (DataField<?> dataField : getElements()) {
                    DataField<?> dataFieldClone = (DataField<?>) dataField.newInstance();
                    dataFieldClone.copyFromDataSource(row);

                    newRow.setObject(dataFieldClone.getAlias(), dataFieldClone.getValue());
                }
            }
        }

        public void addLevel(String name) {
            if (!levelMap.containsKey(name.toLowerCase())) {
                levelMap.put(name.toLowerCase(), (Level<?>) getElement(name.toLowerCase()));
            }
        }

        public void addAttribute(String name) {
            if (!attributeMap.containsKey(name.toLowerCase())) {
                attributeMap.put(name.toLowerCase(), (Attribute<?>) getElement(name.toLowerCase()));
            }
        }

        public void addMeasure(String name) {
            if (!measureMap.containsKey(name.toLowerCase())) {
                measureMap.put(name.toLowerCase(), (Measure<?>) getElement(name.toLowerCase()));
            }
        }

        private void sum(DataNode node, DataRow row) {

            for (Measure<?> measure : measureMap.values()) {
                BigDecimal value1 = BaseFunction.nvl(node.getBigDecimal(measure.getName()), new BigDecimal(0));
                BigDecimal value2 = BaseFunction.nvl(row.getBigDecimal(measure.getName()), new BigDecimal(0));
                node.setBigDecimal(measure.getName(), value1.add(value2));
            }

        }

        public DataNode recalc() {
            DataNode rootNode = new Node();

            if (dataTable.size() > 0) {

                // Ordino i dati
                for (Level<?> level : levelMap.values()) {
                    dataTable.addSortingRule(level.getAlias(), level.getSortType());
                }

                for (Attribute<?> attribute : attributeMap.values()) {
                    dataTable.addSortingRule(attribute.getAlias(), SortingRule.SORT_ASC_NULLS_LAST);
                    break;
                }

                dataTable.applySort();

                // Inizializzazioni
                dataTable.setCurrentRow(0);
                List<DataNode> groupList = new ArrayList<>();
                groupList.add(rootNode);
                for (int i = 1; i <= levelMap.values().size(); i++) {
                    Node dataNode = new Node(dataTable.getRow());
                    groupList.add(dataNode);
                }

                DataRow oldRow = new Row();
                for (DataRow currentRow : dataTable) {
                    sum(rootNode, currentRow);

                    // Determino il livello di rottura e Calcolo i totali
                    int i = 1;
                    boolean rottura = false;
                    for (Level<?> level : levelMap.values()) {
                        DataNode groupNode = groupList.get(i);

                        if (rottura || !BaseFunction.equals(currentRow.getObject(level.getAlias()), oldRow.getObject(level.getAlias()))) {
                            // Rottura
                            rottura = true;
                            DataNode node = new Node(currentRow);
                            groupList.set(i, node);
                            (groupList.get(i - 1)).addChild(node);

                        } else {
                            sum(groupNode, currentRow);
                        }
                        i++;
                    }

                    (groupList.get(groupList.size() - 1)).addChild(new Node(currentRow));
                    oldRow = currentRow;
                }

            }

            return rootNode;
        }

    }
}
