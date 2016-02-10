package company.scheduler;

import org.quartz.JobKey;

/**
 * Created by Yevhen on 2015-08-02.
 */
public interface JobKeyFactory {
    public JobKey get();
}
