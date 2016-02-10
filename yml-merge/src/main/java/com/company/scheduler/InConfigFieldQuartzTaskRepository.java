package com.company.scheduler;

import com.company.config.MergerConfig;
import company.scheduler.QuartzTask;
import company.scheduler.QuartzTaskRepository;

import java.util.List;

/**
 * @author Yevhen
 */
public class InConfigFieldQuartzTaskRepository implements QuartzTaskRepository {

    private NextFireTimeStorage storage;

    public InConfigFieldQuartzTaskRepository(NextFireTimeStorage storage) {
        this.storage = storage;
    }

    @Override
    public List<QuartzTask> get() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(QuartzTask task) {
        MergerConfig config = new ExtractMergeConfigFromJobDetail().extract(task.jobDetail());
        storage.saveNextFireTime(config.getId(), task.trigger().getStartTime().getTime());
    }

    @Override
    public void delete(QuartzTask task) {
        throw new UnsupportedOperationException();
    }
}
