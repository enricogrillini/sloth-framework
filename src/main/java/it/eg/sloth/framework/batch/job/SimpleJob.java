package it.eg.sloth.framework.batch.job;

import it.eg.sloth.framework.batch.jobmessage.JobMessage;
import it.eg.sloth.framework.batch.jobmessage.JobMessageSeverity;
import it.eg.sloth.framework.batch.jobmessage.JobStatus;
import it.eg.sloth.framework.batch.scheduler.SchedulerSingleton;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.common.message.Message;
import it.eg.sloth.framework.common.message.MessageList;
import it.eg.sloth.framework.monitor.MonitorSingleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.text.MessageFormat;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2021 Enrico Grillini
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
@Getter
public abstract class SimpleJob implements Job {

    private static final String MESSAGE_START = "Elaborazione {0} avviata correttamente!";
    private static final String MESSAGE_END = "Elaborazione {0} terminata correttamente!";
    private static final String MESSAGE_END_ALERT = "Elaborazione {0} terminata avvertimenti!";
    private static final String MESSAGE_ABORTED = "Elaborazione {0} abortita!";

    Integer executionId;
    String group;
    String name;
    JobMessageSeverity maxSeverity;

    private void log(JobMessageSeverity severity, String message, String detail, int progress, JobStatus status) throws FrameworkException {
        if (severity.hasHigerSeverity(maxSeverity)) {
            maxSeverity = severity;
        }
        SchedulerSingleton.getInstance().getJobMessageManger().updateMessage(getExecutionId(), severity, message, detail, progress, status);
    }

    public void log(String message, String detail, int progress) throws FrameworkException {
        log(JobMessageSeverity.INFO, message, detail, progress, JobStatus.RUNNING);
    }


    public void warn(String message, String detail, int progress) throws FrameworkException {
        log(JobMessageSeverity.WARN, message, detail, progress, JobStatus.RUNNING);
    }

    public void log(MessageList messageList, int progress) throws FrameworkException {
        for (Message message : messageList) {
            log(JobMessageSeverity.fromLevel(message.getSeverity()), message.getDescription(), message.getSubDescription(), progress, JobStatus.RUNNING);
        }
    }

    @Override
    public void execute(JobExecutionContext context) {
        long eventid = 0;

        try {
            log.info("IN {}", getClass().getName());
            maxSeverity = JobMessageSeverity.INFO;
            eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.JOB, getClass().getName(), null);

            if (context.getTrigger().getJobDataMap().containsKey(JobMessage.EXECUTION_ID)) {
                executionId = context.getTrigger().getJobDataMap().getInt(JobMessage.EXECUTION_ID);
            } else {
                JobMessage jobMessage = SchedulerSingleton.getInstance().getJobMessageManger().createMessage(context.getJobDetail().getKey().getGroup(), context.getJobDetail().getKey().getName());
                executionId = jobMessage.getExecutionId();
            }

            group = context.getJobDetail().getKey().getGroup();
            name = context.getJobDetail().getKey().getName();

            log(JobMessageSeverity.INFO, MessageFormat.format(MESSAGE_START, group + "." + name), "", 0, JobStatus.RUNNING);
            service(context);
            if (maxSeverity.hasHigerSeverity(JobMessageSeverity.INFO)) {
                log(JobMessageSeverity.INFO, MessageFormat.format(MESSAGE_END_ALERT, group + "." + name), "", 100, JobStatus.TERMINATED_WITH_ALERT);
            } else {
                log(JobMessageSeverity.INFO, MessageFormat.format(MESSAGE_END, group + "." + name), "", 100, JobStatus.TERMINATED);
            }
        } catch (Exception e) {
            try {
                log(JobMessageSeverity.ERROR, MessageFormat.format(MESSAGE_ABORTED, group + "." + name), e.getMessage(), 100, JobStatus.ABORTED);
                log.error("ERROR {}: {} - {}", getClass().getName(), e.getMessage(), e);
            } catch (FrameworkException e1) {
                log.error("ERROR {}: {} - {}", getClass().getName(), e1.getMessage(), e1);
            }
        } finally {
            log.info("OUT {}", getClass().getName());
            MonitorSingleton.getInstance().endEvent(eventid);
        }

    }

    public abstract void service(JobExecutionContext context) throws FrameworkException;

}
