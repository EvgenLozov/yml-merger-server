package company.scheduler;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;

/**
 * Created by yevhen on 08.01.16.
 */
public interface QuartzTask {
    JobKey jobKey();
    JobDetail jobDetail();
    Trigger trigger();
}
