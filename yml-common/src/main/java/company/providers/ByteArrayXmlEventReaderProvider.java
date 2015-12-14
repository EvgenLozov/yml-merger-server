package company.providers;

import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;

public class ByteArrayXmlEventReaderProvider implements XMLEventReaderProvider {

    byte[] bytes;
    String encoding;

    public ByteArrayXmlEventReaderProvider(byte[] bytes, String encoding) {
        this.bytes = bytes;
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader get() {
        XMLInputFactory ifactory = XMLInputFactory.newFactory();
        ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        try {
            return ifactory.createXMLEventReader(new ByteArrayInputStream(bytes), encoding);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
