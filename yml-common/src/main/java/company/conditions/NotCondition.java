package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by user50 on 19.07.2015.
 */
public class NotCondition<T> implements EventCondition<T> {

    EventCondition<T> condition;

    public NotCondition(EventCondition<T> condition) {
        this.condition = condition;
    }

    @Override
    public boolean isSuitable(T event) throws XMLStreamException {
        return !condition.isSuitable(event);
    }
}
