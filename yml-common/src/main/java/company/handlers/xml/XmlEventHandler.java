package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public interface XmlEventHandler {
    void handle(XMLEvent event) throws XMLStreamException;
}
