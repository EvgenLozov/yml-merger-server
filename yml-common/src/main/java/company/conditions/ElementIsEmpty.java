package company.conditions;

import company.util.XmlEventUtil;

import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ElementIsEmpty implements Predicate<List<XMLEvent>> {

    String elementName;

    public ElementIsEmpty(String elementName) {
        this.elementName = elementName;
    }

    @Override
    public boolean test(List<XMLEvent> xmlEventList) {
        Optional<String> text = XmlEventUtil.getTextOfElement(xmlEventList, elementName);

        return !text.isPresent() || text.get().trim().isEmpty();

    }
}
