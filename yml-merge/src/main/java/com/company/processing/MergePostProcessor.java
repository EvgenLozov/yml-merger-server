package com.company.processing;

import com.company.config.FilterParameter;
import com.company.config.MergerConfig;
import company.Currency;
import company.bytearray.ByteArrayPostProcessor;
import company.bytearray.RenamingSaveIntoFile;
import company.config.Config;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MergePostProcessor implements ByteArrayPostProcessor {

    MergerConfig config;

    public MergePostProcessor(MergerConfig config) {
        this.config = config;
    }

    @Override
    public void process(byte[] bytes) throws IOException, XMLStreamException {
        new File(config.getOutputFile()).mkdirs();

        for (Currency currency : config.getCurrencies()) {
            byte[] processed = new ChangeCurrencyProcessor( config.getEncoding(), currency, config.getOldPrice(), config.getFilterParameter() ).process(bytes);

            processed = new OffersFilter(config, currency).process(processed);

            new RenamingSaveIntoFile(config.getOutputFile()+"/"+currency.getFileName()).process(processed);
        }
    }
}
