package com.company.operators;

import company.handlers.xml.buffered.BufferXmlEventOperator;
import company.util.XmlEventUtil;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Optional;

public class AddContentToDescription implements BufferXmlEventOperator {

    OfferDescriptionProvider descriptionProvider;
    XMLEventFactory xmlEventFactory;

    public AddContentToDescription(OfferDescriptionProvider descriptionProvider, XMLEventFactory xmlEventFactory) {
        this.descriptionProvider = descriptionProvider;
        this.xmlEventFactory = xmlEventFactory;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).isStartElement() && events.get(i).asStartElement().getName().getLocalPart().equals("description") &&
                    events.get(i+1).isEndElement() && events.get(i+1).asEndElement().getName().getLocalPart().equals("description"))
            {
                Optional<String> url = XmlEventUtil.getTextOfElement(events, "url");

                if (!url.isPresent())
                    return events;

                events.add(i + 1, xmlEventFactory.createCharacters(descriptionProvider.get(url.get())));
            }
        }

        return events;
    }
}
