package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 24.07.2015.
 */
public class SuccessiveXmlEventHandler implements XmlEventHandler {

    List<XmlEventHandler> handlers;

    public SuccessiveXmlEventHandler(List<XmlEventHandler> sequenceOfHandlers) {
        this.handlers = sequenceOfHandlers;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        for (XmlEventHandler handler : handlers) {
            handler.handle(event);
        }
    }
}
