package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.util.XMLEventConsumer;
import java.util.function.Predicate;

/**
 * Відповідальність - записати подію в out якщо виконуються умови описані в condition
 */
public class ConditionalXmlEventWriter implements XmlEventHandler {

    private XMLEventConsumer out;
    private Predicate<XMLEvent> condition;

    public ConditionalXmlEventWriter(XMLEventConsumer out, Predicate<XMLEvent> condition) {
        this.out = out;
        this.condition = condition;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (condition.test(event))
            out.add(event);
    }
}
