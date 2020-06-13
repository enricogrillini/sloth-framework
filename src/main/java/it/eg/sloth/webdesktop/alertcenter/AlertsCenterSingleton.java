package it.eg.sloth.webdesktop.alertcenter;

import it.eg.sloth.webdesktop.alertcenter.model.Alert;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
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
 *
 * @author Enrico Grillini
 */
@Slf4j
public class AlertsCenterSingleton {

    private static AlertsCenterSingleton instance = null;

    List<Alert> list;

    private AlertsCenterSingleton() {
        list = new ArrayList<>();
        log.info("AlertCenter inizializzato");
    }

    public static synchronized AlertsCenterSingleton getInstance() {
        if (instance == null) {
            instance = new AlertsCenterSingleton();
        }

        return instance;
    }

    public synchronized void clear() {
        list.clear();
    }

    public synchronized void add(Alert alert) {
        list.add(alert);
    }

    public Collection<Alert> getList() {
        return new ArrayList<>(list);
    }

}
