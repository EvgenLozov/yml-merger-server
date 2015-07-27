package company;

import company.conditions.EventCondition;
import company.conditions.FalseCondition;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

public class StAXService {

    private XMLEventReaderProvider readerProvider;

    public StAXService(XMLEventReaderProvider readerProvider) {
        this.readerProvider = readerProvider;
    }

    public void process(XmlEventHandler eventHandler, EventCondition<XMLEvent> stopConditions) throws XMLStreamException {
        XMLEventReader in = readerProvider.get();

        while(in.hasNext()){
            XMLEvent e = in.nextEvent();
            eventHandler.handle(e);

            if (stopConditions.isSuitable(e))
                break;
        }
    }

    public void process(XmlEventHandler eventHandler) throws XMLStreamException {
        process(eventHandler, new FalseCondition<XMLEvent>());
    }
}
