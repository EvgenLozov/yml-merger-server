package company.handlers.aggregated;

import company.Currency;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * Created by user50 on 26.07.2015.
 */
public class ChangeCurrencyHandler implements AggregatedXmlEventHandler {

    Currency currency;
    XMLEventWriter mergedOut;

    public ChangeCurrencyHandler(Currency currency, XMLEventWriter mergedOut) {
        this.currency = currency;
        this.mergedOut = mergedOut;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        for (XMLEvent event : events) {
            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("currency"))
                changeCurrency(event);

            mergedOut.add(event);
        }


    }

    private void changeCurrency(XMLEvent event)
    {
        Iterator<Attribute> attributesIterator = event.asStartElement().getAttributes();

        while (attributesIterator.hasNext()){
            Attribute attribute = attributesIterator.next();
            if(attribute.getName().toString().equals("id")){
                setAttributeValue(attribute, currency.name());
            }
        }
    }

    private void setAttributeValue(Attribute attribute, String newValue) {
        try {
            Field attrValue = attribute.getClass().getDeclaredField("fValue");
            attrValue.setAccessible(true);
            attrValue.set(attribute, newValue);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
