package com.company;

import com.company.operators.ModifyOfferDescription;
import com.company.operators.OfferDescriptionProvider;
import company.conditions.AfterElement;
import company.conditions.EndElement;
import company.conditions.OnceTime;
import company.conditions.StartElement;
import company.handlers.xml.ConditionalXmlEventWriter;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.buffered.BufferedXmlEventHandler;
import company.handlers.xml.insert.SimpleXmlEventSupplier;
import company.handlers.xml.insert.XmlEventInserter;
import company.handlers.xml.insert.XmlEventSupplier;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Arrays;

public class ModifierXmlEventHandlerProvider {

    ModifierConfig config;
    Writer writer;

    public ModifierXmlEventHandlerProvider(ModifierConfig config, Writer writer) {
        this.config = config;
        this.writer = writer;
    }

    public XmlEventHandler get() throws FileNotFoundException, UnsupportedEncodingException, XMLStreamException {
        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
        XMLEventWriter xmlEventWriter = oFactory.createXMLEventWriter(writer);

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();

        XmlEventHandler handler = new ConditionalXmlEventWriter(xmlEventWriter, (event)-> true );

        OfferDescriptionProvider provider = new OfferDescriptionProvider(config.getTemplate(),"utf-8");
        handler = new BufferedXmlEventHandler(handler, new StartElement("offer"), new EndElement("offer"), new ModifyOfferDescription(provider) );
        XmlEventSupplier supplier = new SimpleXmlEventSupplier(new OnceTime<>(new AfterElement("offers")),
                ()-> Arrays.asList(eventFactory.createDTD("\n\t\t"),
                        eventFactory.createStartElement("","","offer"),
                        eventFactory.createEndElement("","","offer"),
                        eventFactory.createDTD("\n\t\t")));

        handler = new XmlEventInserter(handler, supplier);

        return handler;
    }

}
