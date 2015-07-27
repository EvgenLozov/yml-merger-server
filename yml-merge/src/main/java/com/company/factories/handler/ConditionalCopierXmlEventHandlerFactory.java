package com.company.factories.handler;

import company.Factory;
import company.conditions.*;
import company.handlers.xml.ConditionalXmlEventWriter;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventConsumer;
import java.util.ArrayList;
import java.util.List;

/**
 * the factory construct XmlEventHandler that copy all events excluding elements offer and categories
 */
public class ConditionalCopierXmlEventHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventConsumer out;
    List<String> elementsToExclude;

    public ConditionalCopierXmlEventHandlerFactory(XMLEventConsumer out, List<String> elementsToExclude) {
        this.out = out;
        this.elementsToExclude = elementsToExclude;
    }

    @Override
    public XmlEventHandler get() {
        List<EventCondition<XMLEvent>> conditions = new ArrayList<>();

        for (String element : elementsToExclude)
            conditions.add(new NotCondition(new InElementCondition(element)));

        EventCondition<XMLEvent> condition = new AndCondition(conditions);

        return new ConditionalXmlEventWriter(out, condition);
    }
}
