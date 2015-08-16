package com.company.factories.handler;

import company.Currency;
import company.Factory;
import company.conditions.TrueCondition;
import company.handlers.aggregated.AggregatedXmlEventHandler;
import company.handlers.aggregated.AggregatedXmlEventWriter;
import company.handlers.aggregated.ChangeOfferCurrency;
import company.handlers.aggregated.SuccessiveAggregatedEventHandler;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import java.util.ArrayList;
import java.util.List;

public class ChangeOfferCurrencyHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventWriter out;
    Currency currency;

    public ChangeOfferCurrencyHandlerFactory(XMLEventWriter out, Currency currency) {
        this.out = out;
        this.currency = currency;
    }

    @Override
    public XmlEventHandler get() {
        List<XmlEventHandler> handlers = new ArrayList<>();

        List<AggregatedXmlEventHandler> offersHandlers = new ArrayList<>();
        offersHandlers.add(new ChangeOfferCurrency(currency));
        offersHandlers.add(new AggregatedXmlEventWriter(out, new TrueCondition<>()));


        handlers.add(new AggregatedXmlEventNotifier(new SuccessiveAggregatedEventHandler(offersHandlers), "offer"));

        return new SuccessiveXmlEventHandler(handlers);
    }
}
