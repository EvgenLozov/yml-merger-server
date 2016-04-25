package company.handlers.xml;

import company.DeleteOldPrices;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * @author Yevhen
 */
public class OldResultsCleanerXmlEventHandler implements XmlEventHandler {
    String firstResultName;
    XmlEventHandler handler;

    public OldResultsCleanerXmlEventHandler(String firstResultName, XmlEventHandler handler) {
        this.firstResultName = firstResultName;
        this.handler = handler;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        handler.handle(event);
        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("yml_catalog")){
            new DeleteOldPrices().delete(firstResultName);
        }
    }
}
