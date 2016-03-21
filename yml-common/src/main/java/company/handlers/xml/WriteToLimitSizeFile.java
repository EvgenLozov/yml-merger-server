package company.handlers.xml;


import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Naya on 19.03.2016.
 */
public class WriteToLimitSizeFile implements XmlEventHandler {
    String outputDir;
    String encoding;
    ByteArrayOutputStream commonPart1;
    ByteArrayOutputStream offers;
    ByteArrayOutputStream commonPart2;
    XMLEventWriter commonPart1EventWriter;
    XMLEventWriter offersEventWriter;
    XMLEventWriter commonPart2EventWriter;
    long limitSize;
    int i = 0;
    List<XMLEventWriter> xmlEventWriters;


    public WriteToLimitSizeFile(String outputDir, String encoding, long limitSize) throws XMLStreamException {
        this.outputDir = outputDir;
        this.encoding = encoding;

        commonPart1 = new ByteArrayOutputStream();
        offers = new ByteArrayOutputStream();
        commonPart2 = new ByteArrayOutputStream();

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
        try {
            Writer writer = new OutputStreamWriter(commonPart1, encoding);
            commonPart1EventWriter = oFactory.createXMLEventWriter(writer);

            Writer writer2 = new OutputStreamWriter(offers, encoding);
            commonPart1EventWriter = oFactory.createXMLEventWriter(writer2);

            Writer writer3 = new OutputStreamWriter(commonPart2, encoding);
            commonPart2EventWriter = oFactory.createXMLEventWriter(writer3);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error of the writers creating ");
        }

        xmlEventWriters = new ArrayList<>();
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        XMLEventWriter xmlEventWriter;
        if (xmlEventWriters.isEmpty()){
             initWriter();
        }
        if (i==1) {

           // xmlEventWriters.get(i - 1).
        }



    }

    private XMLEventWriter initWriter() throws  XMLStreamException {

        XMLEventWriter xmlEventWriter = null;
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(outputDir+"/output"+i+".xml"), encoding);
            XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
            xmlEventWriter = oFactory.createXMLEventWriter(writer);
            xmlEventWriters.add(xmlEventWriter);
            i++;
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            throw new RuntimeException("Error of the writers creating ");
        }
        return xmlEventWriter;
    }



}
