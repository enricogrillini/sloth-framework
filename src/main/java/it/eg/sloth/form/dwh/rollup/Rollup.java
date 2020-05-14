package it.eg.sloth.form.dwh.rollup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.eg.sloth.db.datasource.DataNode;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.node.Node;
import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.datasource.table.sort.SortingRule;
import it.eg.sloth.form.base.AbstractElements;
import it.eg.sloth.form.base.Element;
import it.eg.sloth.form.dwh.Attribute;
import it.eg.sloth.form.dwh.Level;
import it.eg.sloth.form.dwh.Measure;
import it.eg.sloth.form.fields.field.DataField;
import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.casting.Casting;
import it.eg.sloth.framework.common.exception.BusinessException;
import it.eg.sloth.framework.common.exception.BusinessExceptionType;

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

    public DataTable<?> getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable<?> dataTable) throws BusinessException {
        try {
            this.dataTable = dataTable;
            calculate();
        } catch (CloneNotSupportedException e) {
            throw new BusinessException(BusinessExceptionType.ROLLUP_CALCULATE_ERROR, e);
        }
    }

    public List<Attribute<?>> getAttributes() {
        List<Attribute<?>> list = new ArrayList<Attribute<?>>();

        for (Element element : this) {
            if (element instanceof Attribute) {
                list.add((Attribute<?>) element);
            }
        }

        return list;
    }

    public List<Level<?>> getLevels() {
        List<Level<?>> list = new ArrayList<Level<?>>();

        for (Element element : this) {
            if (element instanceof Level) {
                list.add((Level<?>) element);
            }
        }

        return list;
    }

    public List<Measure<?>> getMeasures() {
        List<Measure<?>> list = new ArrayList<Measure<?>>();

        for (Element element : this) {
            if (element instanceof Measure) {
                list.add((Measure<?>) element);
            }
        }

        return list;
    }

    public DataNode getDataNode() {
        return dataNode;
    }

    private void calculate() throws CloneNotSupportedException, BusinessException {
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

        public RollupCalculator(DataTable<?> dataTable) throws CloneNotSupportedException, BusinessException {
            setDataTable(dataTable);
            this.levelMap = new LinkedHashMap<String, Level<?>>();
            this.attributeMap = new LinkedHashMap<String, Attribute<?>>();
            this.measureMap = new LinkedHashMap<String, Measure<?>>();
        }

        public void setDataTable(DataTable<?> dataTable) throws CloneNotSupportedException, BusinessException {
            this.dataTable = new Table();

            for (DataRow row : dataTable) {
                DataRow newRow = this.dataTable.add();

                for (DataField<?> dataField : getElements()) {
                    DataField<?> dataFieldClone = (DataField<?>) dataField.clone();
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
                BigDecimal value1 = (BigDecimal) BaseFunction.nvl(node.getBigDecimal(measure.getName()), new BigDecimal(0));
                BigDecimal value2 = (BigDecimal) BaseFunction.nvl(row.getBigDecimal(measure.getName()), new BigDecimal(0));
                node.setBigDecimal(measure.getName(), value1.add(value2));
            }

        }

        public DataNode recalc() throws CloneNotSupportedException {
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
                List<DataNode> groupList = new ArrayList<DataNode>();
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
                        DataNode groupNode = (DataNode) groupList.get(i);

                        if (rottura || !BaseFunction.equals(currentRow.getObject(level.getAlias()), oldRow.getObject(level.getAlias()))) {
                            // Rottura
                            rottura = true;
                            DataNode node = new Node(currentRow);
                            groupList.set(i, node);
                            ((DataNode) groupList.get(i - 1)).addChild(node);

                        } else {
                            sum(groupNode, currentRow);
                        }
                        i++;
                    }

                    ((DataNode) groupList.get(groupList.size() - 1)).addChild(new Node(currentRow));
                    oldRow = currentRow;
                }

            }

            return rootNode;
        }

    }
}
