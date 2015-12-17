package company.handlers.xml.buffered;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


/*
* якщо елемент elementName відсутній у списку events, він буде добавлений перед останім евентом.
* */

public class AddElementIfAbsent implements BufferXmlEventOperator {

    String elementName;
    XMLEventFactory xmlEventFactory;
    Optional<String> elementTextContent;

    public AddElementIfAbsent(String elementName, XMLEventFactory xmlEventFactory, Optional<String> elementTextContent) {
        this.elementName = elementName;
        this.xmlEventFactory = xmlEventFactory;
        this.elementTextContent = elementTextContent;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {
        for (XMLEvent event : events)
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName))
                return events;

        events.add(events.size() - 1, xmlEventFactory.createStartElement("", "", elementName));

        if (elementTextContent.isPresent())
            events.add(events.size() - 1, xmlEventFactory.createCharacters(elementTextContent.get()));

        events.add(events.size() - 1, xmlEventFactory.createEndElement("", "", elementName));
        events.add(events.size() - 1, xmlEventFactory.createDTD("\n\t\t"));

        return events;
    }
}
