package it.eg.sloth.framework.job;

import it.eg.sloth.framework.FrameComponent;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.List;

/**
 * Singleton per la gestione della configurazione applicativa
 *
 * @author Enrico Grillini
 */
@Slf4j
public class SchedulerSingleton extends FrameComponent {

    static SchedulerSingleton instance = null;

    Scheduler scheduler = null;

    private SchedulerSingleton() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    public static synchronized SchedulerSingleton getInstance() throws SchedulerException {
        if (instance == null) {
            synchronized (SchedulerSingleton.class) {
                if (instance == null) {
                    instance = new SchedulerSingleton();
                }
            }
        }

        return instance;
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

    public List<JobExecutionContext> getCurrentlyExecutingJobs() throws SchedulerException {
        return scheduler.getCurrentlyExecutingJobs();
    }

}
