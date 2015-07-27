package company.handlers.xml;

import company.handlers.aggregated.AggregatedXmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user50 on 19.07.2015.
 */
public class AggregatedXmlEventNotifier implements XmlEventHandler {

    AggregatedXmlEventHandler eventHandler;
    String elementName;

    List<XMLEvent> offer = new ArrayList<>();
    private boolean inOffer;

    public AggregatedXmlEventNotifier(AggregatedXmlEventHandler eventHandler, String elementName) {
        this.eventHandler = eventHandler;
        this.elementName = elementName;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {

        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName))
        {
            offer.add(event);
            inOffer = true;
        }else if (inOffer)
            offer.add(event);

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(elementName))
        {
            eventHandler.handle(offer);
            offer.clear();
            inOffer = false;
        }
    }
}
