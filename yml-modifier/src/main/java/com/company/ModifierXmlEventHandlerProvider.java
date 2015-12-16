package com.company;

import com.company.handlers.OffersCategoryIdModifier;
import com.company.operators.AddContentToDescription;
import com.company.operators.ModifyOfferDescription;
import com.company.operators.OfferDescriptionProvider;
import company.conditions.AfterElement;
import company.conditions.EndElement;
import company.conditions.OnceTime;
import company.conditions.StartElement;
import company.handlers.xml.AttributeValueModifier;
import company.handlers.xml.ConditionalXmlEventWriter;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.buffered.AddElementIfAbsent;
import company.handlers.xml.buffered.BufferXmlEventOperator;
import company.handlers.xml.buffered.BufferedXmlEventHandler;
import company.handlers.xml.buffered.ComplexBufferXmlEventOperator;
import company.handlers.xml.insert.SimpleXmlEventSupplier;
import company.handlers.xml.insert.XmlEventInserter;
import company.handlers.xml.insert.XmlEventSupplier;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<XmlEventHandler> handlers = new ArrayList<>();
        if (config.isModifyOfferId())
            handlers.add(new AttributeValueModifier("offer","id", (old)-> config.getOfferIdPrefix()+old));

        if (config.isModifyCategoryId()) {
            handlers.add(new AttributeValueModifier("category", "id", (old) -> config.getCategoryIdPrefix() + old));
            handlers.add(new AttributeValueModifier("category", "parentId", (old) -> config.getCategoryIdPrefix() + old));
            handlers.add(new OffersCategoryIdModifier(config.getCategoryIdPrefix()));
        }

        handlers.add(new ConditionalXmlEventWriter(xmlEventWriter, (event)-> true ));

        XmlEventHandler handler = new SuccessiveXmlEventHandler(handlers);

        if (config.isModifyDescription())
            handler = new BufferedXmlEventHandler(handler, new StartElement("offer"), new EndElement("offer"), provideDescriptionModification() );

        XmlEventSupplier supplier = new SimpleXmlEventSupplier(new OnceTime<>(new AfterElement("offers")),
                ()-> Arrays.asList(eventFactory.createDTD("\n\t\t"),
                        eventFactory.createStartElement("","","offer"),
                        eventFactory.createEndElement("","","offer"),
                        eventFactory.createDTD("\n\t\t")));

        handler = new XmlEventInserter(handler, supplier);

        return handler;
    }

    private BufferXmlEventOperator provideDescriptionModification()
    {
        OfferDescriptionProvider provider = new OfferDescriptionProvider(config.getTemplate(),"utf-8");

        List<BufferXmlEventOperator> operators = new ArrayList<>();
        operators.add(new AddElementIfAbsent("description",XMLEventFactory.newFactory()));
        operators.add(new ModifyOfferDescription(provider));
        operators.add(new AddContentToDescription(provider, XMLEventFactory.newFactory()));

        return new ComplexBufferXmlEventOperator(operators);
    }

}
