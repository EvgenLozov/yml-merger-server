package company.bytearray;

import javax.xml.stream.XMLStreamException;

/**
 * Created by user50 on 15.08.2015.
 */
public interface ByteArrayProcessor {

    byte[] process(byte[] bytes) throws XMLStreamException;

}
