package it.eg.sloth.framework.model;

import it.eg.sloth.framework.model.Job;
import it.eg.sloth.framework.monitor.MonitorSingleton;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;

@Slf4j
public abstract class BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        long eventid = 0;
        try {
            log.info("IN " + getClass().getName());
            eventid = MonitorSingleton.getInstance().startEvent(MonitorSingleton.JOB, getClass().getName(), null);

            service();

        } catch (Exception e) {
            log.error("ERROR " + getClass().getName(), e);

        } finally {
            log.info("OUT " + getClass().getName());
            MonitorSingleton.getInstance().endEvent(eventid);
        }
    }

    public abstract void service() throws Exception;

}
