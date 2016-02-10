package company.scheduler;

import org.quartz.SchedulerException;

/**
 * Created by yevhen on 09.01.16.
 */
public class PersistentQuartzTasksScheduler implements QuartzTasksScheduler {

    private QuartzTasksScheduler quartzTasksScheduler;
    private QuartzTaskRepository quartzTaskRepository;

    public PersistentQuartzTasksScheduler(QuartzTasksScheduler quartzTasksScheduler,
                                          QuartzTaskRepository quartzTaskRepository) {
        this.quartzTasksScheduler = quartzTasksScheduler;
        this.quartzTaskRepository = quartzTaskRepository;
    }

    @Override
    public void schedule(QuartzTask task) throws SchedulerException {
        quartzTaskRepository.save(task);
        quartzTasksScheduler.schedule(task);
    }

    @Override
    public void delete(QuartzTask task) throws SchedulerException {
        quartzTaskRepository.delete(task);
        quartzTasksScheduler.delete(task);
    }

    @Override
    public boolean isScheduled(QuartzTask task) throws SchedulerException {
        return quartzTasksScheduler.isScheduled(task);
    }
}
