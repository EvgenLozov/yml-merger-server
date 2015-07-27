package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by user50 on 19.07.2015.
 */
public class TrueCondition<T> implements EventCondition<T> {
    @Override
    public boolean isSuitable(T event) throws XMLStreamException {
        return true;
    }
}
