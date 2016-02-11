package com.company.repository;

import com.company.config.MergerConfig;
import company.config.*;

import java.util.List;

public class MergerConfigRepository implements ConfigRepository<MergerConfig> {

    ConfigRepository<MergerConfig> repository;
    PswSecurity pswSecurity = new PswSecurity();

    public MergerConfigRepository(ConfigRepository<MergerConfig> repository) {
        this.repository = repository;
    }

    @Override
    public List<MergerConfig> list() {
        List<MergerConfig> configs = repository.list();
        configs.forEach(pswSecurity::decodePsw);

        return configs;
    }

    @Override
    public void save(MergerConfig config) {
        pswSecurity.encodePsw(config);
        config.setOutputFile("prices/" + config.getName().replaceAll("/", "-").replaceAll(":", "-"));

        repository.save(config);
    }

    @Override
    public MergerConfig create(MergerConfig config) {
        pswSecurity.encodePsw(config);
        config.setOutputFile("prices/" + config.getName().replaceAll("/", "-").replaceAll(":", "-"));

        return repository.create(config);
    }

    @Override
    public void delete(String id) {
        repository.delete(id);
    }

    @Override
    public MergerConfig get(String id) {
        MergerConfig config = repository.get(id);
        pswSecurity.decodePsw(config);
        return config;
    }
}
