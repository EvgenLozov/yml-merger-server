package com.company;

import company.config.ConfigRepository;
import company.config.PswSecurity;

import java.io.File;
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
        String dir = "prices/"+config.getName().replaceAll("/", "-").replaceAll(":", "-");
        new File(dir).mkdirs();
        config.setOutputDir(dir);
        pswSecurity.encodePsw(config);
        configRepository.save(config);
    }

    @Override
    public ModifierConfig create(ModifierConfig config) {
        String dir = "prices/"+config.getName().replaceAll("/", "-").replaceAll(":", "-");
        new File(dir).mkdirs();
        config.setOutputDir(dir);
        pswSecurity.encodePsw(config);
        return  configRepository.create(config);
    }

    @Override
    public void delete(String id) {
        configRepository.delete(id);
    }

    @Override
    public ModifierConfig get(String id) {
        ModifierConfig config = configRepository.get(id);
        pswSecurity.decodePsw(config);
        return config;
    }
}
