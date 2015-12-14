package company.conditions;

import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

public class StartElement implements Predicate<XMLEvent> {

    String name;

    public StartElement(String name) {
        this.name = name;
    }

    @Override
    public boolean test(XMLEvent xmlEvent) {
        return xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(name);
    }
}
