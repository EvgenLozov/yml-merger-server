package company.handlers.xml;

import company.StAXService;
import company.conditions.EventCondition;
import company.conditions.XmlEventCondition;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.Predicate;

/**
 * Відповідальність класу - при виконанні деяких умов під час обробки деякого xml-файлу запустити інший подібний
 * процес обробки
 */
public class XmlEventHandlingProcessTrigger implements XmlEventHandler {

    Predicate<XMLEvent> condition;
    StAXService staxService;
    XmlEventHandler xmlEventHandler;

    public XmlEventHandlingProcessTrigger(Predicate<XMLEvent> condition, StAXService staxService, XmlEventHandler xmlEventHandler) {
        this.condition = condition;
        this.staxService = staxService;
        this.xmlEventHandler = xmlEventHandler;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (condition.test(event))
            staxService.process(xmlEventHandler);
    }
}
