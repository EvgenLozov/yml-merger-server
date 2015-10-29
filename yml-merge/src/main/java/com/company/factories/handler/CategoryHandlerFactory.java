package com.company.factories.handler;

import company.Factory;
import company.conditions.AllowedCategory;
import company.conditions.AndCondition;
import company.conditions.FirstTimeFindCategory;
import company.handlers.aggregated.AggregatedXmlEventWriter;
import company.handlers.xml.AggregatedXmlEventNotifier;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

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
        List<Predicate<List<XMLEvent>>> conditions = new ArrayList<>();
        conditions.add(new FirstTimeFindCategory(addedCategories));
        conditions.add(new AllowedCategory(allowedCategories));

        AndCondition<List<XMLEvent>> writeCategoryCondition = new AndCondition<>(conditions);

        return new AggregatedXmlEventNotifier(new AggregatedXmlEventWriter(out, writeCategoryCondition), "category");
    }
}
