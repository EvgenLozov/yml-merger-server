package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;

/**
 * Created by user50 on 26.07.2015.
 */
public class GetAttributeValueOfLastElement implements XmlEventHandler{

    String elementName;
    String attributeName;

    String value;

    public GetAttributeValueOfLastElement(String elementName, String attributeName) {
        this.elementName = elementName;
        this.attributeName = attributeName;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName))
        {
            value = getValue(event.asStartElement());
        }
    }

    public String getValue() {
        return value;
    }

    private String getValue(StartElement startElement) {
        Iterator<Attribute> attributesIterator = startElement.getAttributes();

        while (attributesIterator.hasNext()){
            Attribute attribute = attributesIterator.next();
            if(attribute.getName().toString().equals("id")){
                return attribute.getValue();
            }
        }

        throw new RuntimeException("Currency element doesn't have attribute 'id'");
    }
}
