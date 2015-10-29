package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

/**
 * Created by user50 on 26.07.2015.
 */
public class InElementCondition implements Predicate<XMLEvent> {
    String elementName;

    boolean isInElement;

    public InElementCondition(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean test(XMLEvent xmlEvent)  {
        if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(elementName))
        {
            isInElement = true;

            return false;
        }

        if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(elementName))
        {
            isInElement = false;

            return false;
        }

        return isInElement;
    }
}
