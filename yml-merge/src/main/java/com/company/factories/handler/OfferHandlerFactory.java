package com.company.factories.handler;

import company.Factory;
import company.conditions.OfferBelongToCategories;
import company.handlers.aggregated.AggregatedXmlEventHandler;
import company.handlers.aggregated.AggregatedXmlEventWriter;
import company.handlers.aggregated.SuccessiveAggregatedEventHandler;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OfferHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventWriter out;
    Set<String> allowedCategories;

    public OfferHandlerFactory(XMLEventWriter out, Set<String> allowedCategories) {
        this.out = out;
        this.allowedCategories = allowedCategories;
    }

    @Override
    public XmlEventHandler get() {
        List<XmlEventHandler> handlers = new ArrayList<>();

        List<AggregatedXmlEventHandler> offersHandlers = new ArrayList<>();
        offersHandlers.add(new AggregatedXmlEventWriter(out, new OfferBelongToCategories(allowedCategories)));

        handlers.add(new AggregatedXmlEventNotifier(new SuccessiveAggregatedEventHandler(offersHandlers), "offer"));

        return new SuccessiveXmlEventHandler(handlers);
    }
}
