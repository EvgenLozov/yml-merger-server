package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

/**
 * Повертає true, якщо елемент elementName зустрічався хоча би раз
 */
public class AfterElement implements Predicate<XMLEvent> {

    String elementName;

    boolean after;

    public AfterElement(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean test(XMLEvent xmlEvent)  {
        if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(elementName))
        {
            after = true;

            return false;
        }

        return after;
    }
}
