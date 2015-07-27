package company.handlers.xml;


import company.conditions.EventCondition;
import company.conditions.XmlEventCondition;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventConsumer;

/**
 * Відповідальність - записати подію в out якщо виконуються умови описані в condition
 */
public class ConditionalXmlEventWriter implements XmlEventHandler {

    private XMLEventConsumer out;
    private EventCondition<XMLEvent> condition;

    public ConditionalXmlEventWriter(XMLEventConsumer out, EventCondition<XMLEvent> condition) {
        this.out = out;
        this.condition = condition;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (condition.isSuitable(event))
            out.add(event);
    }
}
