package com.company.repository;

import com.company.config.ConfigGroup;
import company.config.ConfigRepository;

import java.util.List;

/**
 * @author Yevhen
 */
public class SetEpocheStartConfigGroupRepository implements ConfigRepository<ConfigGroup> {

    private ConfigRepository<ConfigGroup> wrapped;

    public SetEpocheStartConfigGroupRepository(ConfigRepository<ConfigGroup> wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public List<ConfigGroup> list() {
        return wrapped.list();
    }

    @Override
    public void save(ConfigGroup config) {
        config.setEpocheStart(System.currentTimeMillis());
        wrapped.save(config);
    }

    @Override
    public ConfigGroup create(ConfigGroup config) {
        config.setEpocheStart(System.currentTimeMillis());
        return wrapped.create(config);
    }

    @Override
    public void delete(String id) {
        wrapped.delete(id);
    }

    @Override
    public ConfigGroup get(String id) {
        return wrapped.get(id);
    }
}
