package company.bytearray;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by user50 on 15.08.2015.
 */
public interface ByteArrayPostProcessor {

    void process(byte[] bytes) throws IOException, XMLStreamException;
}
