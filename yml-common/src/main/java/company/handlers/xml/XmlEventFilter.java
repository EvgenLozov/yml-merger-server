package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

public class XmlEventFilter implements XmlEventHandler{

    XmlEventHandler handler;
    Predicate<XMLEvent> condition;

    public XmlEventFilter(XmlEventHandler handler, Predicate<XMLEvent> condition) {
        this.handler = handler;
        this.condition = condition;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (condition.test(event))
            handler.handle(event);

    }
}
