package company.handlers.xml.buffered;

import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.function.Predicate;

/*
* if list of events satisfy conditions in predicate then delegate process to operator, else do nothing
* */
public class PredicateOperator implements BufferXmlEventOperator {

    BufferXmlEventOperator operator;
    Predicate<List<XMLEvent>> predicate;

    public PredicateOperator(BufferXmlEventOperator operator, Predicate<List<XMLEvent>> predicate) {
        this.operator = operator;
        this.predicate = predicate;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {
        if (predicate.test(events))
            return operator.apply(events);

        return events;
    }
}
