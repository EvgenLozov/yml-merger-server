package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;


/**
 * Created by user50 on 04.07.2015.
 */
public class ElementCondition implements XmlEventCondition {

    String elementName;

    boolean isInElement;

    public ElementCondition(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean isSuitable(XMLEvent xmlEvent) throws XMLStreamException {
        if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(elementName))
        {
            isInElement = true;

            return true;
        }

        if (xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(elementName))
        {
            isInElement = false;

            return true;
        }

        return isInElement;
    }
}
