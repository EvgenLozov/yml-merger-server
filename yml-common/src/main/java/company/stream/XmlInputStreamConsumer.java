package company.stream;

import company.StAXService;
import company.handlers.xml.XmlEventHandler;
import company.providers.InputStreamXmlEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.InputStream;
import java.util.function.Consumer;

public class XmlInputStreamConsumer implements Consumer<InputStream> {

    String encoding;
    XmlEventHandler xmlEventHandler;

    public XmlInputStreamConsumer(String encoding, XmlEventHandler xmlEventHandler) {
        this.encoding = encoding;
        this.xmlEventHandler = xmlEventHandler;
    }

    @Override
    public void accept(InputStream inputStream) {
        StAXService stAXService = new StAXService( new InputStreamXmlEventReaderProvider(inputStream, encoding));

        try {
            stAXService.process(xmlEventHandler);
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
    }
}
