package com.company;

import com.company.repository.PswSecurity;
import company.config.ConfigRepository;

import java.util.List;

/**
 * Created by Naya on 30.01.2016.
 */
public class ModifierConfigRepository implements ConfigRepository<ModifierConfig> {

    ConfigRepository<ModifierConfig> configRepository;
    PswSecurity pswSecurity = new PswSecurity();

    public ModifierConfigRepository(ConfigRepository<ModifierConfig> configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public List<ModifierConfig> list() {
        List<ModifierConfig> configs = configRepository.list();
        configs.forEach(pswSecurity::decodePsw);
        return configs;
    }

    @Override
    public void save(ModifierConfig config) {
        pswSecurity.encodePsw(config);
        configRepository.save(config);
    }

    @Override
    public ModifierConfig create(ModifierConfig config) {
        config.setOutputDir("price/"+config.getName());
        pswSecurity.encodePsw(config);
        return  configRepository.create(config);
    }

    @Override
    public void delete(String id) {
        configRepository.delete(id);
    }

    @Override
    public ModifierConfig get(String id) {
        return configRepository.get(id);
    }
}
