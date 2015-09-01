package com.company.allowedcategories;

import company.handlers.aggregated.AggregatedXmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CategoriesCollectorV2 implements AggregatedXmlEventHandler {

    private Set<Category> categories;

    public CategoriesCollectorV2(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        Iterator<Attribute> attributesIterator = events.get(0).asStartElement().getAttributes();

        String id = null;
        String parentId = null;

        while (attributesIterator.hasNext()){
            Attribute attribute = attributesIterator.next();
            if(attribute.getName().toString().equals("id"))
                id = attribute.getValue();
            if(attribute.getName().toString().equals("parentId"))
                parentId = attribute.getValue();
        }

        String name = getName(events);


        categories.add(new Category(id, parentId, name));
    }

    private String getName(List<XMLEvent> events)
    {
        String name = "";

        for (XMLEvent event : events) {
            if (event.isCharacters())
                name +=  event.asCharacters().getData();
        }

        return name;
    }
}
