package company.handlers.xml.insert;

import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Optional;

public interface XmlEventSupplier {

    List<XMLEvent> supply(XMLEvent xmlEvent);
}
