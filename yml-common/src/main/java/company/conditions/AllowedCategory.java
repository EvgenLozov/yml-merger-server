package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by user50 on 26.07.2015.
 */
public class AllowedCategory implements Predicate<List<XMLEvent>> {
    Set<String> allowedCategories;

    public AllowedCategory(Set<String> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }

    @Override
    public boolean test(List<XMLEvent> event)  {
        return allowedCategories.contains(getCategoryId(event));
    }

    private String getCategoryId(List<XMLEvent> category) {

        for (XMLEvent xmlEvent : category) {
            if (!xmlEvent.isStartElement() || !xmlEvent.asStartElement().getName().getLocalPart().equals("category"))
                continue;

            Iterator<Attribute> attributesIterator = xmlEvent.asStartElement().getAttributes();

            while (attributesIterator.hasNext()){
                Attribute attribute = attributesIterator.next();
                if (attribute.getName().toString().equals("id"))
                    return attribute.getValue();

            }
        }

        throw new RuntimeException("Id ia required attribute of category");
    }
}
