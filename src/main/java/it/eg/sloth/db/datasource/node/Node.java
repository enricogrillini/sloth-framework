package it.eg.sloth.db.datasource.node;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.eg.sloth.db.datasource.DataNode;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataSource;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.db.datasource.row.Row;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
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
public class Node extends Row implements DataNode {

    private boolean open;
    private List<DataNode> childs;
    private int nodeCount;

    public Node() {
        super();
        clear();
    }

    public Node(DataSource dataSource) {
        this();
        copyFromDataSource(dataSource);
    }

    @Override
    public boolean isOpen() {
        return open;
    }

    @Override
    public void collapse() {
        open = false;

        for (DataNode node : childs) {
            node.collapse();
        }
    }

    @Override
    public void expand() {
        open = true;
    }

    @Override
    public void addChild(DataNode node) {
        childs.add(node);
    }

    @Override
    public List<DataNode> getChilds() {
        return childs;
    }

    @Override
    public boolean hasChilds() {
        return !getChilds().isEmpty();
    }

    @Override
    public void clear() {
        super.clear();
        open = true;
        childs = new ArrayList<>();
    }

    @Override
    public void loadChildFromTableInterface(DataTable<?> table, String levelName) throws SQLException {
        clear();

        List<Node> nodi = new ArrayList<>();
        nodi.add(this);

        for (DataRow dataRow : table) {
            int livello = (dataRow.getBigDecimal(levelName)).intValue();

            Node node = new Node();
            node.loadFromDataSource(dataRow);

            if (livello >= nodi.size())
                nodi.add(node);
            else
                nodi.set(livello, node);

            (nodi.get(livello - 1)).addChild(node);
        }
    }

    private DataNode getOpenNode(DataNode node, int nodeIndex) {
        if (nodeIndex == nodeCount)
            return node;
        if (!node.isOpen())
            return null;

        for (DataNode dataNode : node.getChilds()) {
            nodeCount++;
            DataNode childNode = getOpenNode(dataNode, nodeIndex);

            if (childNode != null)
                return childNode;
        }

        return null;
    }

    @Override
    public DataNode getOpenNode(int nodeIndex) {
        nodeCount = 0;
        return getOpenNode(this, nodeIndex);
    }

    @Override
    public Iterator<DataNode> iterator() {
        return childs.iterator();
    }

}
