package com.company.scheduler;

import com.company.config.MergerConfig;

/**
 * @author Yevhen
 */
public class MergeQuartzTaskFactory {
    public MergeQuartzTask create(MergerConfig config) {
        return new MergeQuartzTask(config, new MergeConfigJobKeyFactory(config));
    }
}
