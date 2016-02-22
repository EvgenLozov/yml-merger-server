package com.company.scheduler;

import com.company.config.MergerConfig;

/**
 * Created by Naya on 20.02.2016.
 */
public class MergeQuartzTaskFactory {
      public MergeQuartzTask create(MergerConfig config) {
        return new MergeQuartzTask(config, new MergeConfigJobKeyFactory(config));
    }
}