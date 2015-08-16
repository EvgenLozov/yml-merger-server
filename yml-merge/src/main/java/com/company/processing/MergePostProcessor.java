package com.company.processing;

import company.Currency;
import company.bytearray.ByteArrayPostProcessor;
import company.bytearray.RenamingSaveIntoFile;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class MergePostProcessor implements ByteArrayPostProcessor {

    String encoding;
    List<Currency> currencies;
    String foulder;

    public MergePostProcessor(String encoding, List<Currency> currencies, String foulder) {
        this.encoding = encoding;
        this.currencies = currencies;
        this.foulder = foulder;
    }

    @Override
    public void process(byte[] bytes) throws IOException, XMLStreamException {

        for (Currency currency : currencies) {
            byte[] processed = new ChangeCurrencyProcessor( encoding, currency ).process(bytes);

            new RenamingSaveIntoFile(foulder+"/"+currency.name()).process(processed);
        }
    }
}
