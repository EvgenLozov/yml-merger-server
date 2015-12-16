package company.handlers.xml.buffered;

import javax.xml.stream.events.XMLEvent;
import java.util.List;

public class ComplexBufferXmlEventOperator implements BufferXmlEventOperator {

    Iterable<BufferXmlEventOperator> operators;

    public ComplexBufferXmlEventOperator(Iterable<BufferXmlEventOperator> operators) {
        this.operators = operators;
    }

    @Override
    public List<XMLEvent> apply(List<XMLEvent> events) {

        for (BufferXmlEventOperator operator : operators)
            events = operator.apply(events);

        return events;
    }
}
