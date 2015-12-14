package company.conditions;

import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

public class EndElement implements Predicate<XMLEvent> {

    String name;

    public EndElement(String name) {
        this.name = name;
    }

    @Override
    public boolean test(XMLEvent xmlEvent) {
        return xmlEvent.isEndElement() && xmlEvent.asEndElement().getName().getLocalPart().equals(name);
    }
}
