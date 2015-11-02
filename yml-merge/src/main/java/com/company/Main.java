package com.company;

import com.company.config.Config;
import com.company.config.ConfigProvider;
import com.company.service.MergeServiceImpl;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        Config config = new ConfigProvider().get();

        new MergeServiceImpl().process(config);
    }
}
