package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by user50 on 24.07.2015.
 */
public interface XmlEventCondition extends EventCondition<XMLEvent> {

    boolean isSuitable(XMLEvent xmlEvent) throws XMLStreamException;
}
