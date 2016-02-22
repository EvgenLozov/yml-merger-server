package company.scheduler;

import org.quartz.Trigger;

/**
 * @author Yevhen
 */
public interface TriggerFactory {
    Trigger get();
}
