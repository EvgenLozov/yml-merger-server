package company.handlers.xml;

import com.sun.xml.internal.stream.events.CharacterEvent;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.function.UnaryOperator;

public class ModifyTextData implements XmlEventHandler {

    UnaryOperator<String> testModificationOperator;

    public ModifyTextData(UnaryOperator<String> testModificationOperator) {
        this.testModificationOperator = testModificationOperator;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        if (!(event instanceof CharacterEvent))
            return;

        String oldText = ((CharacterEvent) event).getData();

        ((CharacterEvent) event).setData(testModificationOperator.apply(oldText));
    }
}
