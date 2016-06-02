package com.company.processing;

import com.company.config.FilterParameter;
import com.company.factories.handler.*;
import company.conditions.AttributeValuePredicate;
import company.conditions.ElementIsEmpty;
import company.conditions.EndElement;
import company.conditions.StartElement;
import company.handlers.xml.buffered.BufferXmlEventOperator;
import company.handlers.xml.buffered.BufferedXmlEventHandler;
import company.handlers.xml.buffered.XmlEventFilter;
import company.providers.ByteArrayXmlEventReaderProvider;
import company.Currency;
import company.Factory;
import company.StAXService;
import company.bytearray.ByteArrayProcessor;
import company.handlers.xml.XmlEventHandler;
import company.util.XmlEventUtil;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ChangeCurrencyProcessor implements ByteArrayProcessor {

    String encoding;
    Currency currency;
    double oldPrice;
    FilterParameter filterParameter;

    public ChangeCurrencyProcessor(String encoding, Currency currency, double oldPrice, FilterParameter filterParameter) {
        this.encoding = encoding;
        this.currency = currency;
        this.oldPrice = oldPrice;
        this.filterParameter = filterParameter;
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
                "offers", new ChangeOfferCurrencyHandlerFactory( mergedOut, currency, oldPrice)));


        StAXService staxService = new StAXService(new ByteArrayXmlEventReaderProvider(bytes, encoding));

        XmlEventHandler xmlEventHandler = new MergeXmlEventHandlerFactory(factories).get();


        new BufferedXmlEventHandler(xmlEventHandler, new StartElement("offer"), new EndElement("offer"), getFilter() );

        staxService.process(xmlEventHandler);

        mergedOut.close();
        byteOutputStream.close();

        return byteOutputStream.toByteArray();
    }

    private BufferXmlEventOperator getFilter()
    {
        if (filterParameter == null || !filterParameter.getCurrencies().contains(currency))
            return events -> events;

        Predicate<List<XMLEvent>> predicate = events->true;

        if (filterParameter.isDescription())
            predicate = predicate.and( new ElementIsEmpty("description").negate());

        if (filterParameter.isImage())
            predicate = predicate.and( new ElementIsEmpty("picture").negate());

        if (filterParameter.isAvailable())
            predicate = predicate.and( new AttributeValuePredicate("offer", "available", "true") );

        return new XmlEventFilter(predicate);
    }
}
