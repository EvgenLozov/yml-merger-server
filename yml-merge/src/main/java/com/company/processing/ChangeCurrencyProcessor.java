package com.company.processing;

import com.company.factories.handler.*;
import com.company.readerproviders.ByteArrayXmlEventReaderProvider;
import company.Currency;
import company.Factory;
import company.StAXService;
import company.bytearray.ByteArrayProcessor;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChangeCurrencyProcessor implements ByteArrayProcessor {

    String encoding;
    Currency currency;

    public ChangeCurrencyProcessor(String encoding, Currency currency) {
        this.encoding = encoding;
        this.currency = currency;
    }

    @Override
    public byte[] process(byte[] bytes) throws XMLStreamException, IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();

        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(byteOutputStream, encoding);


        List<Factory<XmlEventHandler>> factories = new ArrayList<>();
        factories.add(new ChangeYmlCurrencyHandlerFactory(currency));
        factories.add(new ConditionalCopierXmlEventHandlerFactory(mergedOut, Arrays.asList( "offers")));


        factories.add(new ElementWriterHandlerFactory(Arrays.asList(new ByteArrayXmlEventReaderProvider(bytes, encoding)),
                "offers", new ChangeOfferCurrencyHandlerFactory( mergedOut, currency)));


        StAXService staxService = new StAXService(new ByteArrayXmlEventReaderProvider(bytes, encoding));

        XmlEventHandler xmlEventHandler = new MergeXmlEventHandlerFactory(factories).get();
        staxService.process(xmlEventHandler);

        mergedOut.close();
        byteOutputStream.close();

        return byteOutputStream.toByteArray();
    }
}
