package it.eg.sloth.db.datasource;


import java.sql.SQLException;
import java.util.List;

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
public interface DataNode extends DataSource, Iterable<DataNode> {

    boolean isOpen();

    void collapse();

    void expand();

    /**
     * Aggiunge un figlio
     *
     * @param node
     */
    void addChild(DataNode node);

    /**
     * Ritorna la lista dei figli
     *
     * @return
     */
    List<DataNode> getChilds();

    /**
     * Verifica se il nodo ha figli
     *
     * @return
     */
    boolean hasChilds();


    /**
     * Ritorna l'n-esimo nodo aperto
     *
     * @param nodeIndex
     * @return
     */
    DataNode getOpenNode(int nodeIndex);


    void loadChildFromTableInterface(DataTable<?> table, String levelName) throws SQLException;

}
