package company.handlers.xml.buffered;

import javax.xml.stream.events.XMLEvent;
import java.util.List;

public interface BufferXmlEventOperator {

    List<XMLEvent> apply(List<XMLEvent> events);

}
