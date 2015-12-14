package company.handlers.xml.insert;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/*
* Відповідальність - передати event на обробку XmlEventHandler-ру, після чого сзенерувати колекцію подій з допомогою XmlEventSupplier
* і передати на обробку handler-ру. Призначений для вставлення фрагментів xml коду.
* */

public class XmlEventInserter implements XmlEventHandler{

    XmlEventHandler handler;
    XmlEventSupplier supplier;

    public XmlEventInserter(XmlEventHandler handler, XmlEventSupplier supplier) {
        this.handler = handler;
        this.supplier = supplier;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {

        List<XMLEvent> xmlEvents = supplier.supply(event);
        for (XMLEvent generatedEvent : xmlEvents)
            handler.handle(generatedEvent);

        handler.handle(event);
    }

}
