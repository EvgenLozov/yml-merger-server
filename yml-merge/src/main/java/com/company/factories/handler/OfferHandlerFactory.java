package com.company.factories.handler;

import company.Factory;
import company.conditions.NameContainsWords;
import company.conditions.OfferBelongToCategories;
import company.handlers.aggregated.AggregatedXmlEventHandler;
import company.handlers.aggregated.AggregatedXmlEventWriter;
import company.handlers.aggregated.SuccessiveAggregatedEventHandler;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class OfferHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventWriter out;
    Set<String> allowedCategories;
    Set<String> allowedWords;

    public OfferHandlerFactory(XMLEventWriter out, Set<String> allowedCategories, Set<String> allowedWords) {
        this.out = out;
        this.allowedCategories = allowedCategories;
        this.allowedWords = allowedWords;
    }

    @Override
    public XmlEventHandler get() {
        List<XmlEventHandler> handlers = new ArrayList<>();

        List<AggregatedXmlEventHandler> offersHandlers = new ArrayList<>();

        Predicate<List<XMLEvent>> predicate = new OfferBelongToCategories(allowedCategories);
        predicate = predicate.and(new NameContainsWords(allowedWords).negate());

        offersHandlers.add(new AggregatedXmlEventWriter(out, predicate));

        handlers.add(new AggregatedXmlEventNotifier(new SuccessiveAggregatedEventHandler(offersHandlers), "offer"));

        return new SuccessiveXmlEventHandler(handlers);
    }
}
