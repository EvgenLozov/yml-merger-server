package company.handlers.aggregated;

import com.sun.xml.internal.stream.events.CharacterEvent;
import company.Currency;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;
import company.handlers.xml.currency.RenameElementNameHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 26.07.2015.
 */
public class ChangeOfferCurrency implements AggregatedXmlEventHandler {

    Currency currency;

    public ChangeOfferCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void handle(List<XMLEvent> offer) throws XMLStreamException {
        Currency currentCurrency = getOfferCurrency(offer);

        if (currentCurrency == currency)
            return;

        List<XmlEventHandler> handlers = new ArrayList<>();

        handlers.add(new RenameElementNameHandler("price", currentCurrency.getElementName()));
        handlers.add(new RenameElementNameHandler(currency.getElementName(), "price"));

        SuccessiveXmlEventHandler handler = new SuccessiveXmlEventHandler(handlers);

        for (XMLEvent xmlEvent : offer) {
            handler.handle(xmlEvent);
        }

        changeCurrencyId(offer);
    }

    private Currency getOfferCurrency(List<XMLEvent> offer)
    {
        Set<String> prices = new HashSet<>();

        for (XMLEvent xmlEvent : offer) {
            if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().matches("price.*") )
                prices.add(xmlEvent.asStartElement().getName().getLocalPart());
        }

        for (Currency currency : Currency.values()) {
            if (!prices.contains(currency.getElementName()))
                return currency;
        }

        throw new RuntimeException("Unable to determine offer's currency");
    }

    private void changeCurrencyId(List<XMLEvent> offer)
    {
        int index = 0;

        for (XMLEvent xmlEvent : offer) {
            if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals("currencyId"))
            {
                CharacterEvent characterEvent = (CharacterEvent) offer.get(index + 1);
                characterEvent.setData(currency.name());

            }
            index++;
        }


    }



}
