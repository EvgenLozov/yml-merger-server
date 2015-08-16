package com.company.processing;

import com.company.config.Config;
import com.company.config.ConfigProvider;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException {
        Config config = new ConfigProvider().get();

        byte[] bytes = new MergedYmlSource(config).provide();


        ReplaceProcessing processing = new ReplaceProcessing(config.getEncoding(), config.getReplaces());
        bytes = processing.process(bytes);

        new MergePostProcessor(config.getEncoding(), config.getCurrencies(), config.getOutputFile()).process(bytes);
    }


}
