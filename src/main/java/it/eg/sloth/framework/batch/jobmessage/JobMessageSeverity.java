package it.eg.sloth.framework.batch.jobmessage;

import it.eg.sloth.framework.common.message.Level;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 * <p>
 * Singleton per la gestione delle Scedulazioni
 *
 * @author Enrico Grillini
 */
public enum JobMessageSeverity {

    ERROR(3), WARN(2), INFO(1);

    int severity;

    JobMessageSeverity(int severity) {
        this.severity = severity;
    }

    public boolean hasHigerSeverity(JobMessageSeverity jobMessageSeverity) {
        return this.severity > jobMessageSeverity.severity;
    }

    public static JobMessageSeverity fromLevel(Level level) {
        switch (level) {
            case INFO:
            case SUCCESS:
                return INFO;
            case WARN:
                return WARN;
            case ERROR:
                return ERROR;
            default:
                return INFO;
        }
    }
}
