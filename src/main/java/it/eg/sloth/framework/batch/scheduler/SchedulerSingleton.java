package it.eg.sloth.framework.batch.scheduler;

import it.eg.sloth.framework.batch.jobmessage.InMemoryMessageManger;
import it.eg.sloth.framework.batch.jobmessage.JobMessageManger;
import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.List;

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
public class SchedulerSingleton {

    static SchedulerSingleton instance = null;

    Scheduler scheduler = null;
    JobMessageManger jobMessageManger = null;

    private SchedulerSingleton() throws FrameworkException {
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            jobMessageManger = new InMemoryMessageManger();
        } catch (SchedulerException e) {
            throw new FrameworkException(ExceptionCode.GENERIC_SYSTEM_ERROR, "Impossibile istanziare lo scheduler", e);
        }
    }

    public static synchronized SchedulerSingleton getInstance() throws FrameworkException {
        if (instance == null) {
            synchronized (SchedulerSingleton.class) {
                if (instance == null) {
                    instance = new SchedulerSingleton();
                }
            }
        }

        return instance;
    }

    public JobMessageManger getJobMessageManger() {
        return jobMessageManger;
    }

    public void setJobMessageManger(JobMessageManger jobMessageManger) {
        this.jobMessageManger = jobMessageManger;
    }

    /**
     * Avvia lo scheduler
     *
     * @throws SchedulerException
     */
    public synchronized void start() throws SchedulerException {
        log.info("Scheduler start!");
        scheduler.start();
    }

    /**
     * Sospende lo scheduler
     *
     * @throws SchedulerException
     */
    public synchronized void standby() throws SchedulerException {
        log.info("Scheduler standby!");
        scheduler.standby();
    }

    /**
     * Arresta lo scheduler
     *
     * @throws SchedulerException
     */
    public synchronized void shutdown() throws SchedulerException {
        log.info("Scheduler shutdown!");
        scheduler.shutdown();
        scheduler = null;
    }

    public SchedulerStatus getStatus() throws SchedulerException {
        if (scheduler.isStarted()) {
            if (scheduler.isInStandbyMode()) {
                return SchedulerStatus.SUSPENDED;
            } else {
                return SchedulerStatus.ACTIVE;
            }
        } else {
            return SchedulerStatus.STOPPED;
        }
    }

    public synchronized void scheduleJob(JobDetail jobDetail, CronTrigger cronTrigger) throws SchedulerException {
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    public synchronized void schedule(List<JobDetail> jobList, List<Trigger> triggerList) throws SchedulerException {
        scheduler.clear();

        for (JobDetail jobDetail : jobList) {
            scheduler.addJob(jobDetail, true);
        }
        for (Trigger trigger : triggerList) {
            scheduler.scheduleJob(trigger);
        }

    }

    public SchedulerStatistics getSchedulerStatistics() throws SchedulerException {
        SchedulerStatistics schedulerStatistics = new SchedulerStatistics();

        schedulerStatistics.setStatus(getStatus());

        // scheduledJobCount
        int count = 0;
        for (String groupName : scheduler.getJobGroupNames()) {
            count += scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName)).size();
        }
        schedulerStatistics.setScheduledJobCount(count);

        count = 0;
        for (String groupName : scheduler.getTriggerGroupNames()) {
            count += scheduler.getTriggerKeys(GroupMatcher.triggerGroupEquals(groupName)).size();
        }
        schedulerStatistics.setScheduledTriggerCount(count);

        schedulerStatistics.setRunningJob(scheduler.getCurrentlyExecutingJobs().size());
        schedulerStatistics.setTotalExecutions(scheduler.getMetaData().getNumberOfJobsExecuted());

        return schedulerStatistics;
    }

    /**
     * Esegue un Job
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public synchronized void runJob(JobKey jobKey) throws SchedulerException {
        scheduler.triggerJob(jobKey);
    }

    /**
     * Esegue un Job parametrizzato
     *
     * @param jobKey
     * @param data
     * @throws SchedulerException
     */

    public synchronized void runJob(JobKey jobKey, JobDataMap data) throws SchedulerException {
        scheduler.triggerJob(jobKey, data);
    }

    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
        return scheduler.getCurrentlyExecutingJobs();
    }

}
