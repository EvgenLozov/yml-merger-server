package company.handlers.aggregated;

import com.sun.xml.internal.stream.events.CharacterEvent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
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
        String modifiedData = data;

        for (String word : wordsToReplace) {
            modifiedData = modifiedData.replaceAll(word, replacement);
        }

        return modifiedData;
    }
}
