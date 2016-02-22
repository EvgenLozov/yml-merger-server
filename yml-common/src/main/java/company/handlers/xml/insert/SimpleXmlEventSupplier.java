package company.handlers.xml.insert;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleXmlEventSupplier implements XmlEventSupplier {

    Predicate<XMLEvent> generateConditions;
    Supplier<List<XMLEvent>> xmlEventsSupplier;

    public SimpleXmlEventSupplier(Predicate<XMLEvent> generateConditions, Supplier<List<XMLEvent>> xmlEventsSupplier) {
        this.generateConditions = generateConditions;
        this.xmlEventsSupplier = xmlEventsSupplier;
    }

    @Override
    public List<XMLEvent> supply(XMLEvent xmlEvent) {
        if (generateConditions.test(xmlEvent))
            return xmlEventsSupplier.get();

        return new ArrayList<>();
    }
}
