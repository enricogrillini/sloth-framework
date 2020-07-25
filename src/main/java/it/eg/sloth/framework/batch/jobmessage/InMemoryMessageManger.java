package it.eg.sloth.framework.batch.jobmessage;

import it.eg.sloth.framework.common.base.TimeStampUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2020 Enrico Grillini
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
public class InMemoryMessageManger implements JobMessageManger {

    Map<Integer, JobMessage> map;
    int executionIdCounter;

    public InMemoryMessageManger() {
        map = new HashMap<>();
        executionIdCounter = 0;
    }

    @Override
    public synchronized JobMessage createMessage(String groupName, String jobName)  {
        JobMessage jobMessage = JobMessage.builder()
                .executionId(++executionIdCounter)
                .groupName(groupName)
                .jobName(jobName)
                .status(JobStatus.SCHEDULED)
                .progress(0)
                .started(TimeStampUtil.sysdate())
                .updated(TimeStampUtil.sysdate())
                .build();

        map.put(jobMessage.getExecutionId(), jobMessage);

        return jobMessage;
    }

    @Override
    public synchronized JobMessage updateMessage(Integer executionId, String message, String detail, int progress, JobStatus status) {
        JobMessage jobMessage = getMessage(executionId);

        jobMessage.setMessage(message);
        jobMessage.setDetail(detail);
        jobMessage.setProgress(progress);
        jobMessage.setStatus(status);
        jobMessage.setUpdated(TimeStampUtil.sysdate());

        return jobMessage;
    }

    @Override
    public synchronized JobMessage getMessage(Integer executionId) {
        return map.get(executionId);
    }
}
