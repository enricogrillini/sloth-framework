package it.eg.sloth.framework.monitor;

import java.util.GregorianCalendar;

import it.eg.sloth.framework.security.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
public class MonitorEvent {
    private String group;
    private String name;
    private User user;

    @Setter(value = AccessLevel.NONE)
    private long start;

    @Setter(value = AccessLevel.NONE)
    private long end;

    public MonitorEvent(String group, String name, User user) {
        this.group = group;
        this.name = name;
        this.user = user;
        this.start = 0;
        this.end = 0;
    }

    public void start() {
        this.start = new GregorianCalendar().getTimeInMillis();
    }

    public void end() {
        this.end = new GregorianCalendar().getTimeInMillis();
    }

}
