package company;

import company.handlers.xml.XmlEventHandler;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

public class StAXService {

    private XMLEventReaderProvider readerProvider;

    public StAXService(XMLEventReaderProvider readerProvider) {
        this.readerProvider = readerProvider;
    }

    public void process(XmlEventHandler eventHandler, Predicate<XMLEvent> stopConditions) throws XMLStreamException {
        XMLEventReader in = readerProvider.get();

        while(in.hasNext()){
            XMLEvent e = in.nextEvent();
            eventHandler.handle(e);

            if (stopConditions.test(e))
                break;
        }
    }

    public void process(XmlEventHandler eventHandler) throws XMLStreamException {
        process(eventHandler, xmlEvent -> false);
    }
}
