package company.handlers.aggregated;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 24.07.2015.
 */
public interface AggregatedXmlEventHandler {

    void handle(List<XMLEvent> events) throws XMLStreamException;
}
