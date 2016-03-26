package com.company;

import company.config.ConfigRepository;

public class EpochalModifyService {

    private ConfigRepository<ModifierConfig> configRepository;
    private ModifyService modifyService;

    public EpochalModifyService(ConfigRepository<ModifierConfig> configRepository, ModifyService modifyService) {
        this.configRepository = configRepository;
        this.modifyService = modifyService;
    }

    public void process(String configId){
        ModifierConfig config = configRepository.get(configId);
        modifyService.process(config);

        config.setEpocheStart(System.currentTimeMillis());
        configRepository.save(config);
    }
}
