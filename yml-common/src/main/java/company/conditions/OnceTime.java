package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * повертає true лише один раз і при першому виконанні умов описаних в eventCondition
 */
public class OnceTime<T> implements EventCondition<T> {

    EventCondition<T> eventCondition;

    boolean onceSuitable;

    public OnceTime(EventCondition<T> eventCondition) {
        this.eventCondition = eventCondition;
    }

    @Override
    public boolean isSuitable(T event) throws XMLStreamException {
        if (!onceSuitable )
        {
            boolean result = eventCondition.isSuitable(event);

            if (result)
                onceSuitable = true;

            return result;
        }

        return false;
    }
}
