package company.handlers.xml;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;

public class WriteEventIntoStream implements XmlEventHandler{

    String encoding;

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {

    }


}
