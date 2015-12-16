package company.handlers.xml.buffered;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;


/*
* якщо елемент elementName відсутній у списку events, він буде добавлений перед останім евентом.
* */

public class AddElementIfAbsent implements BufferXmlEventOperator {

    String elementName;
    XMLEventFactory xmlEventFactory;

    public AddElementIfAbsent(String elementName, XMLEventFactory xmlEventFactory) {
        this.elementName = elementName;
        this.xmlEventFactory = xmlEventFactory;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {
        for (XMLEvent event : events)
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName))
                return events;

        events.add(events.size() - 1, xmlEventFactory.createStartElement("","",elementName));
        events.add(events.size() - 1, xmlEventFactory.createEndElement("", "", elementName));

        return events;
    }
}
