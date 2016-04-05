package com.company.config;

import company.config.Config;

import java.util.List;

/**
 * @author Yevhen
 */
public class ConfigGroup implements Config {

    private String id;
    private List<String> mergerConfigIds;

    private long epocheStart;
    private long epochePeriod;

    public long getEpocheStart() {
        return epocheStart;
    }

    public void setEpocheStart(long epocheStart) {
        this.epocheStart = epocheStart;
    }

    public long getEpochePeriod() {
        return epochePeriod;
    }

    public void setEpochePeriod(long epochePeriod) {
        this.epochePeriod = epochePeriod;
    }

    public List<String> getMergerConfigIds() {
        return mergerConfigIds;
    }

    public void setMergerConfigIds(List<String> mergerConfigIds) {
        this.mergerConfigIds = mergerConfigIds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
