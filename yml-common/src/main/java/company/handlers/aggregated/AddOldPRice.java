package company.handlers.aggregated;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import java.util.Arrays;
import java.util.List;

public class AddOldPRice implements AggregatedXmlEventHandler {

    XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();

    double oldPrice;

    public AddOldPRice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        if (!isAvailable(events) || isExistOldPRice(events))
            return;

        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");

        StartElement startElement = xmlEventFactory.createStartElement("", "", "oldprice");

        double price = getPrice(events);
        price = (int)(price * (1 + oldPrice));
        Characters textElement = xmlEventFactory.createCharacters(String.valueOf(price));

        EndElement endElement = xmlEventFactory.createEndElement("", "", "oldprice");

        for (int i=0;i<events.size();i++)
            if (events.get(i).isEndElement() &&  events.get(i).asEndElement().getName().getLocalPart().equals("price")) {
                events.addAll(++i, Arrays.asList(end, tab,tab,tab,tab, startElement, textElement, endElement));

                break;
            }

    }

    private boolean isAvailable(List<XMLEvent> offer) {
        StartElement startElement = offer.get(0).asStartElement();

        Attribute attribute = startElement.getAttributeByName(QName.valueOf("available"));

        return attribute == null || !(attribute.getValue().isEmpty() || !Boolean.valueOf(attribute.getValue()));

    }


    private boolean isExistOldPRice(List<XMLEvent> events)
    {
        for (XMLEvent event : events) {
            if (event.isStartElement() &&  event.asStartElement().getName().getLocalPart().equals("oldprice"))
                return true;
        }

        return false;
    }

    private double getPrice(List<XMLEvent> events)
    {
        for (int i=0;i<events.size();i++)
            if (events.get(i).isStartElement() &&  events.get(i).asStartElement().getName().getLocalPart().equals("price")) {
                return Double.valueOf(events.get(i+1).toString());
            }

        return 10000;
    }
}
