package it.eg.sloth.framework.batch.job;

import it.eg.sloth.framework.common.base.TimeStampUtil;

import java.util.HashMap;
import java.util.Map;

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
