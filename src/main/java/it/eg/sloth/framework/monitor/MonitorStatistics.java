package it.eg.sloth.framework.monitor;

import it.eg.sloth.framework.common.base.BaseFunction;
import it.eg.sloth.framework.common.base.StringUtil;
import lombok.Getter;

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
public class MonitorStatistics {

    String group;
    String name;
    long executions;
    long duration;
    long max;

    public MonitorStatistics(String group, String name) {
        this.group = group;
        this.name = name;
        this.executions = 0;
        this.duration = 0;
        this.max = 0;
    }

    public String getShortName() {
        if (BaseFunction.isBlank(getName())) {
            return StringUtil.EMPTY;
        }

        if (getName().contains(".")) {
            return getName().substring(getName().lastIndexOf(".") + 1);
        } else {
            return getName();
        }
    }

    public void update(MonitorEvent monitorEvent) {
        long eventDuration = monitorEvent.getEnd() - monitorEvent.getStart();

        this.duration += eventDuration;
        this.executions++;
        this.max = eventDuration > this.max ? eventDuration : this.max;
    }

    public double getAverage() {
        return getDuration() / (getExecutions() == 0 ? 1 : getExecutions());
    }

}
