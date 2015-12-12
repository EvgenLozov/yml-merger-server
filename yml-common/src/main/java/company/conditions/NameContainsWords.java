package company.conditions;

import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class NameContainsWords implements Predicate<List<XMLEvent>> {

    Set<String> words;

    public NameContainsWords(Set<String> words) {
        this.words = words;
    }

    @Override
    public boolean test(List<XMLEvent> xmlEvents) {
        String name = getName(xmlEvents).toLowerCase();

        for (String word : words) {
            if (name.contains(word.toLowerCase()))
                return true;
        }

        return false;
    }

    private String getName(List<XMLEvent> xmlEvents)
    {
        Iterator<XMLEvent> iterator = xmlEvents.iterator();

        while (iterator.hasNext())
        {
            XMLEvent event = iterator.next();

            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("name"))
                return iterator.next().asCharacters().getData();

        }

        throw new RuntimeException("Offer"+xmlEvents.get(0).asStartElement().toString()+" doesn't contains element 'name'");
    }
}
