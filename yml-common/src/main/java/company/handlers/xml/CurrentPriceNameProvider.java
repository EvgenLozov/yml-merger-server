package company.handlers.xml;

import company.Currency;
import company.DataProvider;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created by user50 on 26.07.2015.
 */
public class CurrentPriceNameProvider implements XmlEventHandler, DataProvider<String> {

    GetAttributeValueOfLastElement getAttribute = new GetAttributeValueOfLastElement("currency", "id");

    @Override
    public String provide() {
        return Currency.valueOf(getAttribute.getValue().toUpperCase()).getElementName();
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        getAttribute.handle(event);

    }
}
