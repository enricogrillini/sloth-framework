package it.eg.sloth.framework.batch.job;

import it.eg.sloth.framework.batch.jobmessage.JobMessage;
import it.eg.sloth.framework.batch.jobmessage.JobStatus;
import it.eg.sloth.framework.batch.scheduler.SchedulerSingleton;
import it.eg.sloth.framework.common.exception.FrameworkException;
import it.eg.sloth.framework.monitor.MonitorSingleton;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
@Slf4j
@Getter
public abstract class SimpleJob implements Job {

    Integer executionId;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long eventid = 0;

        try {
            log.info("IN {}", getClass().getName());
            eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.JOB, getClass().getName(), null);

            if (context.getTrigger().getJobDataMap().containsKey(JobMessage.EXECUTION_ID)) {
                executionId = context.getTrigger().getJobDataMap().getInt(JobMessage.EXECUTION_ID);
            } else {
                JobMessage jobMessage = SchedulerSingleton.getInstance().getJobMessageManger().createMessage(context.getJobDetail().getKey().getGroup(), context.getJobDetail().getKey().getName());

                executionId = jobMessage.getExecutionId();
            }

            service(context);
            SchedulerSingleton.getInstance().getJobMessageManger().updateMessage(getExecutionId(), "Elaborazione " + context.getJobDetail().getKey().getGroup() + "." + context.getJobDetail().getKey().getName() + " terminata correttamente!", "", 100, JobStatus.TERMINATED);

        } catch (FrameworkException e) {
            try {
                SchedulerSingleton.getInstance().getJobMessageManger().updateMessage(getExecutionId(), "Elaborazione " + context.getJobDetail().getKey().getGroup() + "." + context.getJobDetail().getKey().getName() + " abortita!", e.getMessage(), 100, JobStatus.ABORTED);
            } catch (FrameworkException e1) {
                log.error("ERROR {}: {} - {}", getClass().getName(), e1.getExceptionType(), e1.getMessage(), e1);
            }
            log.error("ERROR {}: {} - {}", getClass().getName(), e.getExceptionType(), e.getMessage(), e);
        } finally {
            log.info("OUT {}", getClass().getName());
            MonitorSingleton.getInstance().endEvent(eventid);
        }

    }

    public abstract void service(JobExecutionContext context) throws FrameworkException;

}