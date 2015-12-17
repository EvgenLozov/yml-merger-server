package com.company.handlers;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

public class OffersSeparator implements XmlEventHandler {

    List<XmlEventHandler> handlers;
    int offerPerFile;

    int handledOffers;

    public OffersSeparator(List<XmlEventHandler> handlers, int offerPerFile) {
        this.handlers = handlers;
        this.offerPerFile = offerPerFile;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        int index = Math.min(handledOffers/offerPerFile, handlers.size() - 1);

        handlers.get(index).handle(event);

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offer"))
            handledOffers++;
    }
}
