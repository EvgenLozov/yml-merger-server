package company.conditions;

import company.util.XmlEventUtil;

import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class AttributeValuePredicate implements Predicate<List<XMLEvent>> {

    String elementName;
    String attributeName;
    String attributeValue;

    public AttributeValuePredicate(String elementName, String attributeName, String attributeValue) {
        this.elementName = elementName;
        this.attributeName = attributeName;
        this.attributeValue = attributeValue;
    }

    @Override
    public boolean test(List<XMLEvent> xmlEvents) {
        Optional<String> attValue = XmlEventUtil.getAttributeValue(xmlEvents, elementName, attributeName);

        return attValue.isPresent() && attValue.get().equals(attributeValue);

    }
}
