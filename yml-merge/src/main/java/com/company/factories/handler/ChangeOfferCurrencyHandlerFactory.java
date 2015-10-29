package com.company.factories.handler;

import company.Currency;
import company.Factory;
import company.handlers.aggregated.*;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import java.util.ArrayList;
import java.util.List;

public class ChangeOfferCurrencyHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventWriter out;
    Currency currency;
    double oldPrice;

    public ChangeOfferCurrencyHandlerFactory(XMLEventWriter out, Currency currency, double oldPrice) {
        this.out = out;
        this.currency = currency;
        this.oldPrice = oldPrice;
    }

    @Override
    public XmlEventHandler get() {
        List<XmlEventHandler> handlers = new ArrayList<>();

        List<AggregatedXmlEventHandler> offersHandlers = new ArrayList<>();
        offersHandlers.add(new ChangeOfferCurrency(currency));

        if (oldPrice > 0)
            offersHandlers.add(new AddOldPRice(oldPrice));

        offersHandlers.add(new AggregatedXmlEventWriter(out, xmlEvents -> true));


        handlers.add(new AggregatedXmlEventNotifier(new SuccessiveAggregatedEventHandler(offersHandlers), "offer"));

        return new SuccessiveXmlEventHandler(handlers);
    }
}
