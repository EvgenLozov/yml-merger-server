package company.conditions;

import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public class NameContainsWords implements Predicate<List<XMLEvent>> {

    Set<String> words;

    public NameContainsWords(Set<String> words) {
        this.words = words;
    }

    @Override
    public boolean test(List<XMLEvent> xmlEvents) {
        Optional<String> name = getName(xmlEvents);

        if (!name.isPresent())
            return false;

        for (String word : words) {
            if (name.get().toLowerCase().contains(word.toLowerCase()))
                return true;
        }

        return false;
    }

    private Optional<String> getName(List<XMLEvent> xmlEvents)
    {
        Iterator<XMLEvent> iterator = xmlEvents.iterator();

        while (iterator.hasNext())
        {
            XMLEvent event = iterator.next();

            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("name"))
                return Optional.of(iterator.next().asCharacters().getData());

        }

        return Optional.empty();
    }
}
