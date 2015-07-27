package company.handlers.aggregated;

import company.conditions.EventCondition;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 24.07.2015.
 */
public class AggregatedXmlEventWriter implements AggregatedXmlEventHandler {

    XMLEventWriter out;
    EventCondition<List<XMLEvent>> condition;

    XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();

    public AggregatedXmlEventWriter(XMLEventWriter out, EventCondition<List<XMLEvent>> condition) {
        this.out = out;
        this.condition = condition;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        if (condition.isSuitable(events))
        {
            for (XMLEvent event : events)
                out.add(event);

            out.add(xmlEventFactory.createCharacters("\n\t\t"));
        }
    }
}
