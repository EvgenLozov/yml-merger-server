package company.handlers.xml.currency;

import company.Currency;
import company.DataProvider;
import company.handlers.xml.XmlEventHandler;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;

/**
 * Created by user50 on 26.07.2015.
 */
public class RenameElementNameHandler implements XmlEventHandler {

    String from;
    DataProvider<String> newElementNameProvider;

    public RenameElementNameHandler(String from, DataProvider<String> newElementNameProvider) {
        this.from = from;
        this.newElementNameProvider = newElementNameProvider;
    }

    public RenameElementNameHandler(String from, final String to) {
        this.from = from;
        this.newElementNameProvider = new SimpleElementNameProvider(to);
    }



    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(from))
        {
            rename(event.asStartElement().getName());
        }

        if (event.isEndElement() && event.asEndElement().getName().getLocalPart().equals(from))
        {
            rename(event.asEndElement().getName());
        }
    }

    private void rename(QName qName)
    {
        try {
            Field elementName = qName.getClass().getDeclaredField("localPart");
            elementName.setAccessible(true);
            elementName.set(qName, newElementNameProvider.provide());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    private static class SimpleElementNameProvider implements DataProvider<String>
    {
        String elementName;

        private SimpleElementNameProvider(String elementName) {
            this.elementName = elementName;
        }

        @Override
        public String provide() {
            return elementName;
        }
    }
}
