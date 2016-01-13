package com.company.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user50 on 21.06.2015.
 */
public class ConfigProvider {

    public MergerConfig get(){
        InputStream inputStream = getClass().getResourceAsStream("/config.json");
        try {
            return new ObjectMapper().readValue(inputStream, MergerConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read /config.json", e);
        }
    }
}
