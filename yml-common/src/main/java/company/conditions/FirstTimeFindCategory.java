package company.conditions;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Повертає true якщо категорія зустрічається вперше вперше, інкаше false
 */
public class FirstTimeFindCategory implements EventCondition<List<XMLEvent>> {

    Set<String> foundCategories;

    public FirstTimeFindCategory(Set<String> addedCategories) {
        this.foundCategories = addedCategories;
    }

    @Override
    public boolean isSuitable(List<XMLEvent> category) throws XMLStreamException {
        String categoryId =  getCategoryId(category);
        
        boolean result = !foundCategories.contains(categoryId);
        
        if (result)
            foundCategories.add(categoryId);
        
        return result;
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
