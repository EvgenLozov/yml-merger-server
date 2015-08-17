package company.handlers.aggregated;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
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
        if (isExistOldPRice(events))
            return;

        XMLEvent end = xmlEventFactory.createDTD("\n");
        XMLEvent tab = xmlEventFactory.createDTD("\t");

        StartElement startElement = xmlEventFactory.createStartElement("", "", "oldprice");

        double price = getPrice(events);
        price = (int)(price * (1 + oldPrice/100));
        Characters textElement = xmlEventFactory.createCharacters(String.valueOf(price));

        EndElement endElement = xmlEventFactory.createEndElement("", "", "oldprice");

        for (int i=0;i<events.size();i++)
            if (events.get(i).isEndElement() &&  events.get(i).asEndElement().getName().getLocalPart().equals("price")) {
                events.addAll(++i, Arrays.asList(end, tab,tab,tab,tab, startElement, textElement, endElement));

                break;
            }

    }

    private boolean isExistOldPRice(List<XMLEvent> events)
    {
        for (XMLEvent event : events) {
            if (event.isStartElement() &&  event.asStartElement().getName().getLocalPart().equals("OldPrice"))
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

        throw new RuntimeException("Unable to find element price");
    }
}
