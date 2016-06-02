package company.handlers.xml.buffered;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class XmlEventFilter implements BufferXmlEventOperator {

    Predicate<List<XMLEvent>> predicate;

    public XmlEventFilter(Predicate<List<XMLEvent>> predicate) {
        this.predicate = predicate;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {
        if (!predicate.test(events))
            return new ArrayList<>();

        return events;
    }
}
