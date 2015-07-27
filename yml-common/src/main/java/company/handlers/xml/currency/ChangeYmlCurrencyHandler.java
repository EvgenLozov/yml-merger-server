package company.handlers.xml.currency;

import company.Currency;
import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by user50 on 26.07.2015.
 */
public class ChangeYmlCurrencyHandler implements XmlEventHandler {

    Currency currency;

    public ChangeYmlCurrencyHandler(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("currency"))
            changeCurrency(event);

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
