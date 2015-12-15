package com.company.scheduler;

import com.company.config.Config;
import org.quartz.Trigger;

/**
 * @author Yevhen
 */
public interface TriggerFactory {
    Trigger get(Config config);
}
