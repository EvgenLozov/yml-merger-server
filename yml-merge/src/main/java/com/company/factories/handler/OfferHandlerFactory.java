package com.company.factories.handler;

import com.company.config.Replace;
import company.Currency;
import company.DataProvider;
import company.Factory;
import company.conditions.OfferBelongToCategories;
import company.conditions.TrueCondition;
import company.handlers.aggregated.*;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.CurrentPriceNameProvider;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.currency.RenameElementNameHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OfferHandlerFactory implements Factory<XmlEventHandler> {

    Currency currency;
    XMLEventWriter out;
    Set<String> allowedCategories;
    List<Replace> replaces;

    public OfferHandlerFactory(Currency currency, XMLEventWriter out, Set<String> allowedCategories, List<Replace> replaces) {
        this.currency = currency;
        this.out = out;
        this.allowedCategories = allowedCategories;
        this.replaces = replaces;
    }

    @Override
    public XmlEventHandler get() {
        List<XmlEventHandler> handlers = new ArrayList<>();

        for (Replace replace : replaces) {
            Set<String> wordToReplace = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            wordToReplace.addAll(replace.getWordsToReplace());

            handlers.add(new AggregatedXmlEventNotifier(new ReplaceWords(wordToReplace, replace.getReplacement()), "description"));
            handlers.add(new AggregatedXmlEventNotifier(new ReplaceWords(wordToReplace, replace.getReplacement()), "name"));
        }

        List<AggregatedXmlEventHandler> offersHandlers = new ArrayList<>();
        offersHandlers.add(new ChangeOfferCurrency(currency));
        offersHandlers.add(new AggregatedXmlEventWriter(out, new OfferBelongToCategories(allowedCategories)));

        handlers.add(new AggregatedXmlEventNotifier(new SuccessiveAggregatedEventHandler(offersHandlers), "offer"));

        return new SuccessiveXmlEventHandler(handlers);
    }
}
