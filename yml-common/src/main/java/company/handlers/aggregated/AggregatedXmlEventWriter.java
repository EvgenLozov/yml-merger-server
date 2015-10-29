package company.handlers.aggregated;


import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by user50 on 24.07.2015.
 */
public class AggregatedXmlEventWriter implements AggregatedXmlEventHandler {

    XMLEventWriter out;
    Predicate<List<XMLEvent>> condition;

    XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();

    public AggregatedXmlEventWriter(XMLEventWriter out, Predicate<List<XMLEvent>> condition) {
        this.out = out;
        this.condition = condition;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        if (condition.test(events))
        {
            for (XMLEvent event : events)
                out.add(event);

            out.add(xmlEventFactory.createCharacters("\n\t\t"));
        }
    }
}
