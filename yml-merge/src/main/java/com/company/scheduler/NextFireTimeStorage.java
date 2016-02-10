package com.company.scheduler;

import com.company.config.MergerConfig;
import company.config.ConfigRepository;

/**
 * @author Yevhen
 */
public class NextFireTimeStorage {

    private ConfigRepository<MergerConfig> repository;

    public NextFireTimeStorage(ConfigRepository<MergerConfig> repository) {
        this.repository = repository;
    }

    public void saveNextFireTime(String configId, long nextFireTime){
        MergerConfig config = repository.get(configId);
        config.setNextFireTime(nextFireTime);
        repository.save(config);

    }
}
