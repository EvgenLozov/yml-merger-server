package company.scheduler;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

/**
 * Created by yevhen on 08.01.16.
 */
public class InMemoryQuartzTasksScheduler implements QuartzTasksScheduler{
    private Scheduler scheduler;

    public InMemoryQuartzTasksScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void schedule(QuartzTask task) throws SchedulerException {
        scheduler.scheduleJob(task.jobDetail(), task.trigger());
    }

    public void delete(QuartzTask task) throws SchedulerException {
        JobKey jobKey = task.jobKey();
        if (scheduler.checkExists(jobKey)){
            scheduler.deleteJob(jobKey);
        }
    }

    public boolean isScheduled(QuartzTask task) throws SchedulerException {
        return scheduler.checkExists(task.jobKey());
    }
}
