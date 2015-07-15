package com.merger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lozov on 15.07.15.
 */
public class ConfigRepository {
    public static final String CONFIG_FILE = "config/config.json";
    private ObjectMapper objectMapper;

    public ConfigRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Config get(){

        try(InputStream inputStream = new FileInputStream(CONFIG_FILE)) {

            return objectMapper.readValue(inputStream, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read " + CONFIG_FILE);
        }
    }

    public void save(Config config){
        File configFile = new File(CONFIG_FILE);
        try {
            objectMapper.writeValue(configFile, config);
        } catch (IOException e) {
            throw new RuntimeException("Unable to write " + CONFIG_FILE);
        }
    }
}
