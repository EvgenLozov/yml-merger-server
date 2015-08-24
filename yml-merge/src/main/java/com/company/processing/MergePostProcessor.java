package com.company.processing;

import company.Currency;
import company.bytearray.ByteArrayPostProcessor;
import company.bytearray.RenamingSaveIntoFile;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MergePostProcessor implements ByteArrayPostProcessor {

    String encoding;
    List<Currency> currencies;
    String foulder;
    double oldPrice;

    public MergePostProcessor(String encoding, List<Currency> currencies, String foulder, double oldPrice) {
        this.encoding = encoding;
        this.currencies = currencies;
        this.foulder = foulder;
        this.oldPrice = oldPrice;
    }

    @Override
    public void process(byte[] bytes) throws IOException, XMLStreamException {
        new File(foulder).mkdirs();

        for (Currency currency : currencies) {
            byte[] processed = new ChangeCurrencyProcessor( encoding, currency, oldPrice ).process(bytes);

            new RenamingSaveIntoFile(foulder+"/"+currency.getFileName()).process(processed);
        }
    }
}
