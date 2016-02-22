package company.handlers.xml;

import com.sun.xml.internal.stream.events.StartElementEvent;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ModifyElementAttributes implements XmlEventHandler {

    String elementName;
    Consumer<Map<QName, Attribute>> attributesModificator;

    public ModifyElementAttributes(String elementName, Consumer<Map<QName, Attribute>> attributesModificator) {
        this.elementName = elementName;
        this.attributesModificator = attributesModificator;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (!event.isStartElement() || !event.asStartElement().getName().getLocalPart().equals(elementName) || !(event instanceof StartElementEvent))
            return;

        StartElementEvent startElementEvent = ((StartElementEvent) event.asStartElement());

        Map<QName, Attribute> attributes = getAttributes(startElementEvent);

        attributesModificator.accept(attributes);
    }

    private Map<QName, Attribute> getAttributes(StartElementEvent startElement)
    {
        try {
            Field field = startElement.getClass().getDeclaredField("fAttributes");
            field.setAccessible(true);
            return (Map)field.get(startElement);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
