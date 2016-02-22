package company.handlers.xml;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/*
* Дозволяє модифікувати атрибут елемента.
 * tagName - назва елемента
 * attributeName - назва атрибута
 * modifyOperation функція, яка на вхід приймає старе значення и повертає модифіковане
* */

public class AttributeValueModifier implements XmlEventHandler {

    String tagName;
    String attributeName;
    UnaryOperator<String> modifyOperation;

    public AttributeValueModifier(String tagName, String attributeName, UnaryOperator<String> modifyOperation) {
        this.tagName = tagName;
        this.attributeName = attributeName;
        this.modifyOperation = modifyOperation;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(tagName) )
        {
            Iterator<Attribute> attributesIterator = event.asStartElement().getAttributes();
            while (attributesIterator.hasNext()){
                Attribute attribute = attributesIterator.next();
                if(attribute.getName().toString().equals(attributeName)){
                    String oldValue = attribute.getValue();
                    setAttributeValue(attribute, modifyOperation.apply(oldValue) );
                }
            }
        }
    }

    private void setAttributeValue(Attribute attribute, String newValue) {
        try {
            Field attrValue = attribute.getClass().getDeclaredField("fValue");
            attrValue.setAccessible(true);
            attrValue.set(attribute, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
