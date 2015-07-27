package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;

/**
 * Created by user50 on 19.07.2015.
 */
public class AndCondition<T> implements EventCondition<T> {

    List<EventCondition<T>> conditions;

    public AndCondition(List<EventCondition<T>> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean isSuitable(T event) throws XMLStreamException {

        for (EventCondition<T> condition : conditions) {
            if (!condition.isSuitable(event))
                return false;
        }

        return true;
    }


}
