package com.company.processing;

import com.company.config.FilterParameter;
import com.company.config.MergerConfig;
import com.company.factories.handler.*;
import company.Currency;
import company.Factory;
import company.StAXService;
import company.bytearray.ByteArrayProcessor;
import company.conditions.*;
import company.config.Config;
import company.handlers.xml.ConditionalXmlEventWriter;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.buffered.BufferXmlEventOperator;
import company.handlers.xml.buffered.BufferedXmlEventHandler;
import company.handlers.xml.buffered.XmlEventFilter;
import company.providers.ByteArrayXmlEventReaderProvider;

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

public class OffersFilter implements ByteArrayProcessor {

    MergerConfig config;
    Currency currency;

    public OffersFilter(MergerConfig config, Currency currency) {
        this.config = config;
        this.currency = currency;
    }

    @Override
    public byte[] process(byte[] bytes) throws XMLStreamException, IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();

        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(byteOutputStream, config.getEncoding());

        StAXService staxService = new StAXService(new ByteArrayXmlEventReaderProvider(bytes, config.getEncoding()));

        XmlEventHandler xmlEventHandler = new ConditionalXmlEventWriter(mergedOut, event -> true);
        xmlEventHandler = new BufferedXmlEventHandler(xmlEventHandler, new StartElement("offer"), new EndElement("offer"), getFilter() );

        staxService.process(xmlEventHandler);

        mergedOut.close();
        byteOutputStream.close();

        return byteOutputStream.toByteArray();
    }

    private BufferXmlEventOperator getFilter()
    {
        FilterParameter filterParameter = config.getFilterParameter();

        Predicate<List<XMLEvent>> filterPredicate = new OfferUniqueness();

        if (filterParameter != null && filterParameter.getCurrencies().contains(currency))
        {
            if (filterParameter.isDescription())
                filterPredicate = filterPredicate.and( new ElementIsEmpty("description").negate());

            if (filterParameter.isImage())
                filterPredicate = filterPredicate.and( new ElementIsEmpty("picture").negate());

            if (filterParameter.isAvailable())
                filterPredicate = filterPredicate.and( new AttributeValuePredicate("offer", "available", "true") );
        }

        return new XmlEventFilter(filterPredicate);
    }
}
