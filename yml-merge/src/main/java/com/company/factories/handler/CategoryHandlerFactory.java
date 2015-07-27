package com.company.factories.handler;

import company.Factory;
import company.conditions.*;
import company.handlers.aggregated.AggregatedXmlEventWriter;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 26.07.2015.
 */
public class CategoryHandlerFactory implements Factory<XmlEventHandler> {

    XMLEventWriter out;
    Set<String> allowedCategories;
    Set<String> addedCategories = new HashSet<>();

    public CategoryHandlerFactory(XMLEventWriter out, Set<String> allowedCategories) {
        this.out = out;
        this.allowedCategories = allowedCategories;
    }

    @Override
    public XmlEventHandler get() {
        List<EventCondition<List<XMLEvent>>> conditions = new ArrayList<>();
        conditions.add(new FirstTimeFindCategory(addedCategories));
        conditions.add(new AllowedCategory(allowedCategories));

        AndCondition<List<XMLEvent>> writeCategoryCondition = new AndCondition<>(conditions);

        return new AggregatedXmlEventNotifier(new AggregatedXmlEventWriter(out, writeCategoryCondition), "category");
    }
}
