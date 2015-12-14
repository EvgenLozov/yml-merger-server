package company.handlers.xml.buffered;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BufferedXmlEventHandler implements XmlEventHandler{

    XmlEventHandler handler;
    Predicate<XMLEvent> startCondition;
    Predicate<XMLEvent> stopCondition;
    BufferXmlEventOperator bufferXmlEventOperator;

    boolean buffering;
    List<XMLEvent> buffer = new ArrayList<>();

    public BufferedXmlEventHandler(XmlEventHandler handler,
                                   Predicate<XMLEvent> startCondition,
                                   Predicate<XMLEvent> stopCondition,
                                   BufferXmlEventOperator bufferXmlEventOperator) {
        this.handler = handler;
        this.startCondition = startCondition;
        this.stopCondition = stopCondition;
        this.bufferXmlEventOperator = bufferXmlEventOperator;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {

        if (startCondition.test(event))
        {
            buffering = true;
            buffer.add(event);

        }else if (buffering)
        {
            buffer.add(event);

            if (stopCondition.test(event)) {
                buffering = false;

                for (XMLEvent xmlEvent : bufferXmlEventOperator.apply(buffer))
                    handler.handle(xmlEvent);

                buffer.clear();
            }
        }else
        {
            handler.handle(event);
        }

    }
}
