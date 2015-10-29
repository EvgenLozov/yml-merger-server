package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by user50 on 26.07.2015.
 */
public class OfferBelongToCategories implements Predicate<List<XMLEvent>> {

    Set<String> categories;

    public OfferBelongToCategories(Set<String> categories) {
        this.categories = categories;
    }

    @Override
    public boolean test(List<XMLEvent> event)  {
        return categories.contains(getCategoryId(event));
    }

    private String getCategoryId(List<XMLEvent> offer)
    {
        int i = 0;

        for (XMLEvent xmlEvent : offer) {
            i++;
            if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().toString().equals("categoryId"))
                return offer.get(i).asCharacters().getData();

        }

        throw new RuntimeException("Unable to find categoryId element");
    }
}
