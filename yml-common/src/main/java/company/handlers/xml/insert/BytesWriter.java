package company.handlers.xml.insert;

import company.handlers.xml.XmlEventHandler;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by Naya on 14.03.2016.
 */
public class BytesWriter implements XmlEventHandler {
    String fileName;
    String commonPart;
    String encoding;
    int limitSize;
    int i = 0;

    XMLEventWriter xmlEventWriter;

    public BytesWriter(String fileName, String commonPart, String encoding, int limitSize) {
        this.fileName = fileName;
        this.commonPart = commonPart;
        this.encoding = encoding;
        this.limitSize = limitSize;

    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {

        if (xmlEventWriter == null) {
            initWriter(fileName + i);
            xmlEventWriter.add(event);
        }
        if (new File(fileName+i).length()>limitSize-500){
           if(event.isEndElement()){
               xmlEventWriter.add(event);
               xmlEventWriter.close();
               initWriter(fileName + i);
           }
           else xmlEventWriter.add(event);
        }

    }

    private void initWriter(String fileName) throws  XMLStreamException {
        try {
            InputStream inputStream = new FileInputStream(commonPart);
            OutputStream outputStream = new FileOutputStream(fileName);
            outputStream.write(inputStream.read());
            inputStream.close();
            outputStream.close();

            Writer writer = new OutputStreamWriter(new FileOutputStream(fileName), encoding);
            XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
            xmlEventWriter = oFactory.createXMLEventWriter(writer);
            i++;
        } catch (UnsupportedEncodingException |FileNotFoundException |FactoryConfigurationError e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
