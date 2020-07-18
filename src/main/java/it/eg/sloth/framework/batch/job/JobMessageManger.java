package it.eg.sloth.framework.batch.job;

import it.eg.sloth.framework.common.exception.FrameworkException;

public interface JobMessageManger {

    JobMessage createMessage(String groupName, String jobName) throws FrameworkException;

    JobMessage updateMessage(Integer executionId, String message, String detail, int progress, JobStatus status) throws FrameworkException;

    JobMessage getMessage(Integer executionId) throws FrameworkException;

}
