package com.merger;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user50 on 21.06.2015.
 */
public class ConfigProvider {

    public Config get(){
        try (InputStream inputStream = new FileInputStream("config/config.json")){

            return new ObjectMapper().readValue(inputStream, Config.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to read /config.json");
        }
    }
}
