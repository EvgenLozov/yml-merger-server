package company.util;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class XmlEventUtil {

    public static Optional<String> getTextOfElement(List<XMLEvent> events, String elementName)
    {
        Optional<Characters> characters = getCharacterEventOfElement(events, elementName);

        if (characters.isPresent())
            return Optional.of(characters.get().getData());

        return Optional.empty();
    }

    public static Optional<Characters> getCharacterEventOfElement(List<XMLEvent> events, String elementName)
    {
        Iterator<XMLEvent> iterator = events.iterator();

        while (iterator.hasNext())
        {
            XMLEvent event = iterator.next();

            if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals(elementName))
                return Optional.of(iterator.next().asCharacters());

        }

        return Optional.empty();
    }

}
