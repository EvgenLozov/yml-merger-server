package com.company.taskscheduler;

import org.quartz.SchedulerException;

/**
 * Created by yevhen on 09.01.16.
 */
public interface QuartzTasksScheduler {
    public void schedule(QuartzTask task) throws SchedulerException;
    public void delete(QuartzTask task) throws SchedulerException;
}
