package it.eg.sloth.framework.batch.jobmessage;

import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class LogMessageManger implements JobMessageManger {


    @Override
    public JobMessage createMessage(String groupName, String jobName) throws FrameworkException {
        return null;
    }

    @Override
    public JobMessage updateMessage(Integer executionId, JobMessageSeverity severity, String message, String detail, int progress, JobStatus status) {
        log.info("executionId {} | progress {} | status {} | - {} {}", executionId, progress, status, message, detail);
        return null;
    }

    @Override
    public JobMessage getMessage(Integer executionId) throws FrameworkException {
        return null;
    }
}
