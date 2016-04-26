package company.stream;

import company.StAXService;
import company.handlers.xml.ConditionalXmlEventWriter;
import company.handlers.xml.SuccessiveXmlEventHandler;
import company.handlers.xml.XmlEventHandler;
import company.providers.InputStreamXmlEventReaderProvider;
import company.stream.storage.Storage;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.Arrays;

public class XmlInputStreamOperator implements InputStreamOperator {

    String encoding;
    XmlEventHandler xmlEventHandler;
    Storage storage;

    public XmlInputStreamOperator(String encoding, XmlEventHandler xmlEventHandler, Storage storage) {
        this.encoding = encoding;
        this.xmlEventHandler = xmlEventHandler;
        this.storage = storage;
    }

    @Override
    public InputStream apply(InputStream inputStream) {
        StAXService stAXService = new StAXService( new InputStreamXmlEventReaderProvider(inputStream, encoding));
        ConditionalXmlEventWriter writerHandler = new ConditionalXmlEventWriter(getXmlEventWriter(), (e)->true);

        try {
            stAXService.process(new SuccessiveXmlEventHandler(Arrays.asList(xmlEventHandler, writerHandler)));

            return storage.getInputStream();
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

    }

    private XMLEventWriter getXmlEventWriter()
    {
        try {
            Writer writer = new OutputStreamWriter(storage.getOutputStream(), encoding);
            XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
            return oFactory.createXMLEventWriter(writer);
        } catch (UnsupportedEncodingException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
