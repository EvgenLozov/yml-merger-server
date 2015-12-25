package com.company.scheduler;

import com.company.config.MergerConfig;
import org.quartz.Trigger;

/**
 * @author Yevhen
 */
public interface TriggerFactory {
    Trigger get(MergerConfig config);
}
