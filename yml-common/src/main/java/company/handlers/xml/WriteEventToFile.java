package company.handlers.xml;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.function.Predicate;
/*
* Дозволяє записувати у файл XMLEvent-ти. При отриманні першого XMLEvent-та потік ініціалізується.
* Потік закривається при виконанні умов conditionToClose.
* */
public class WriteEventToFile implements XmlEventHandler {

    String fileName;
    String encoding;
    Predicate<XMLEvent> conditionToClose;

    XMLEventWriter xmlEventWriter;

    public WriteEventToFile(String fileName, String encoding, Predicate<XMLEvent> conditionToClose) {
        this.fileName = fileName;
        this.encoding = encoding;
        this.conditionToClose = conditionToClose;
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        try {
            if (xmlEventWriter == null)
                initWriter();

            xmlEventWriter.add(event);

            if (conditionToClose.test(event))
                xmlEventWriter.close();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void initWriter() throws  XMLStreamException {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
            XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
            xmlEventWriter = oFactory.createXMLEventWriter(writer);
        } catch (UnsupportedEncodingException|FileNotFoundException|FactoryConfigurationError e) {
            throw new RuntimeException(e);
        }
    }
}
