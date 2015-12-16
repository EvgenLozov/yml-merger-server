package com.company.handlers;

import com.sun.xml.internal.stream.events.CharacterEvent;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class OffersCategoryIdModifier implements XmlEventHandler {

    private String prefix;

    private boolean inCategoryId;

    public OffersCategoryIdModifier(String prefix) {
        this.prefix = prefix;
    }

    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("categoryId") )
        {
            inCategoryId = true;
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("categoryId") )
        {
            inCategoryId = false;
        }

        if (inCategoryId && event.isCharacters())
        {

            String categoryId = ((CharacterEvent) event).getData();

            ((CharacterEvent) event).setData(prefix + categoryId);
        }

    }
}
