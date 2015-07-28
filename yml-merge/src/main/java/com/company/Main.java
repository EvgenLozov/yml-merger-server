package com.company;

import com.company.config.Config;
import com.company.config.ConfigProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        Config config = new ConfigProvider().get();

        new MergeService().process(config);
    }
}
