package company.providers;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;

public class InputStreamXmlEventReaderProvider implements XMLEventReaderProvider {

    InputStream inputStream;
    String encoding;

    public InputStreamXmlEventReaderProvider(InputStream inputStream, String encoding) {
        this.inputStream = inputStream;
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader get() {
        XMLInputFactory ifactory = XMLInputFactory.newFactory();
        ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        try {
            return ifactory.createXMLEventReader(inputStream, encoding);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
