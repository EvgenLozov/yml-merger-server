package company.conditions;

import javax.xml.stream.XMLStreamException;

/**
 * Created by user50 on 26.07.2015.
 */
public class FalseCondition<T> implements EventCondition<T> {
    @Override
    public boolean isSuitable(T event) throws XMLStreamException {
        return false;
    }
}
