package com.merger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Created by lozov on 15.07.15.
 */
public class ConfigRepository {
    public static final String CONFIG_FILE = "config/config.json";
    private ObjectMapper objectMapper;

    public ConfigRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Config> list(){

        try(InputStream inputStream = new FileInputStream(CONFIG_FILE)) {
            CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Config.class);

            return objectMapper.readValue(inputStream, listType);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + CONFIG_FILE);
        }
    }

    public void save(Config config){
        List<Config> configList = list();

        int index = -1;

        for (Config config1 : configList) {
            if (config.getId().equals(config1.getId()))
                index = configList.indexOf(config1);
        }

        if (index < 0)
            return;

        configList.set(index, config);
        updateStorageFile(configList);
    }

    public Config create(Config config) {
        String id = UUID.randomUUID().toString();
        config.setId(id);

        List<Config> configList = list();
        configList.add(config);

        updateStorageFile(configList);

        return config;
    }

    public void delete(String id) {
        List<Config> configList = list();
        Iterator<Config> iterator = configList.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getId().equals(id))
                iterator.remove();
        }
        updateStorageFile(configList);
    }

    public Config get(String id) {
        return null;
    }

    private void updateStorageFile(List<Config> configList) {
        File configFile = new File(CONFIG_FILE);
        try {
            objectMapper.writeValue(configFile, configList);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write " + CONFIG_FILE);
        }
    }
}
