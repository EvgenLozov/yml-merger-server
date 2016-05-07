package com.company;

import com.company.config.MergerConfig;
import com.company.config.ConfigProvider;
import com.company.repository.MergerConfigRepository;
import com.company.service.MergeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import company.config.ConfigRepository;
import company.config.JsonConfigRepository;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        MergerConfig config = new ConfigProvider().get();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        ConfigRepository<MergerConfig> repository = new MergerConfigRepository(new JsonConfigRepository<>("config/config.json",MergerConfig.class,mapper));
        new MergeServiceImpl(repository).process(config);
    }
}
