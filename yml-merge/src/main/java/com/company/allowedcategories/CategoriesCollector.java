package com.company.allowedcategories;


import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by user50 on 11.07.2015.
 */
public class CategoriesCollector implements XmlEventHandler {

    private Set<Category> categories;

    public CategoriesCollector(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("category") )
        {
            Iterator<Attribute> attributesIterator = event.asStartElement().getAttributes();

            String id = null;
            String parentId = null;

            while (attributesIterator.hasNext()){
                Attribute attribute = attributesIterator.next();
                if(attribute.getName().toString().equals("id"))
                    id = attribute.getValue();
                if(attribute.getName().toString().equals("parentId"))
                    parentId = attribute.getValue();
            }
            categories.add(new Category(id, parentId));
        }
    }
}
