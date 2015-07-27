package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Повертає true, якщо елемент elementName зустрічався хоча би раз
 */
public class AfterElement implements XmlEventCondition {

    String elementName;

    boolean after;

    public AfterElement(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean isSuitable(XMLEvent xmlEvent) throws XMLStreamException {
        if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(elementName))
        {
            after = true;

            return false;
        }

        return after;
    }
}
