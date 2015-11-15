package com.company;

import com.company.config.Config;
import com.company.config.ConfigProvider;
import com.company.repository.ConfigRepository;
import com.company.service.MergeServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        Config config = new ConfigProvider().get();

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        new MergeServiceImpl(new ConfigRepository(mapper)).process(config);
    }
}
