package company.handlers.xml;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;


/*
* Підраховує кількість XMLEvent які задовільняються умовам eventCondition
* */
public class EventCounter implements XmlEventHandler {
    Predicate<XMLEvent> eventCondition;

    int count;

    public EventCounter(Predicate<XMLEvent> eventCondition) {
        this.eventCondition = eventCondition;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (eventCondition.test(event))
            count++;
    }

    public int getCount() {
        return count;
    }
}
