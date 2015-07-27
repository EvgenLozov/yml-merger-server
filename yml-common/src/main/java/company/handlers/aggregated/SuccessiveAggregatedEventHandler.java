package company.handlers.aggregated;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 26.07.2015.
 */
public class SuccessiveAggregatedEventHandler implements AggregatedXmlEventHandler {

    List<AggregatedXmlEventHandler> handlers;

    public SuccessiveAggregatedEventHandler(List<AggregatedXmlEventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        for (AggregatedXmlEventHandler handler : handlers) {
            handler.handle(events);
        }
    }
}
