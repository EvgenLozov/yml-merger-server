package company.handlers.xml.insert;

import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class ComplexXmlEventSupplier implements XmlEventSupplier {

    Iterable<XmlEventSupplier> suppliers;

    public ComplexXmlEventSupplier(Iterable<XmlEventSupplier> suppliers) {
        this.suppliers = suppliers;
    }

    @Override
    public List<XMLEvent> supply(XMLEvent xmlEvent) {
        List<XMLEvent> events = new ArrayList<>();

        for (XmlEventSupplier supplier : suppliers)
            events.addAll(supplier.supply(xmlEvent));

        return events;
    }
}
