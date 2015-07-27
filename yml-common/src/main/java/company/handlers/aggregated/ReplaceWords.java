package company.handlers.aggregated;

import com.sun.xml.internal.stream.events.CharacterEvent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 27.07.2015.
 */
public class ReplaceWords implements AggregatedXmlEventHandler {

    Set<String> wordsToReplace;
    String replacement;

    public ReplaceWords(Set<String> wordsToReplace, String replacement) {
        this.wordsToReplace = wordsToReplace;
        this.replacement = replacement;
    }

    @Override
    public void handle(List<XMLEvent> events) throws XMLStreamException {
        for (XMLEvent event : events)
            if (event.isCharacters())
                replace(event.asCharacters());

    }

    private void replace(Characters characters) {
        if (!(characters instanceof CharacterEvent))
            return;

        CharacterEvent characterEvent = (CharacterEvent) characters;
        characterEvent.setData(replace(characters.getData()));
    }

    private String replace(String data) {
        List<String> words = new ArrayList<>();

        for (String word : data.split(" ")) {
            if (wordsToReplace.contains(word))
                words.add(replacement);
            else
                words.add(word);
        }

        return join(words);
    }

    private String join(Iterable<String> strings)
    {
        StringBuilder builder = new StringBuilder();

        for (String string : strings) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(string);
        }

        return builder.toString();
    }
}
