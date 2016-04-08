package company.handlers.xml;


import com.sun.xml.internal.stream.events.CharacterEvent;
import company.conditions.InElementCondition;

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
    List<XMLEvent> commonPart1EventList;
    List<XMLEvent> commonPart2EventList;
    long limitSize;
    int i = 0;
    List<XMLEventWriter> xmlEventWriters;
    List<ByteArrayOutputStream> outputStreamsForWriters;
    Predicate<XMLEvent> conditionToClose;
    Predicate<XMLEvent> commonPartCondition;


    public WriteToLimitSizeFile(String outputDir, String encoding, long limitSize) throws XMLStreamException {
        this.outputDir = outputDir;
        this.encoding = encoding;
        this.limitSize = limitSize;
        commonPart1 = new ByteArrayOutputStream();


        conditionToClose = (event) -> event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("yml_catalog");
        commonPartCondition = new InElementCondition("offers").negate();
        commonPart1EventList = new ArrayList<>();
        commonPart2EventList = new ArrayList<>();

        xmlEventWriters = new ArrayList<>();
        outputStreamsForWriters = new ArrayList<>();
    }

    @Override
    public void handle(XMLEvent event) throws XMLStreamException {
        try {

            if (xmlEventWriters.isEmpty()) {
                if (commonPartCondition.test(event)) {
                    commonPart1EventList.add(event);

                }
                else {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); // fix iniwriter method

                    System.out.print("CommonPart1 has been write");
                    initWriter(outputStream);
                }
            }else {
                if (commonPartCondition.test(event)) {
                    if (conditionToClose.test(event)) {

                        commonPart2EventList.add(event);
                        System.out.println("CommonPart2 has been write");

                        for (XMLEventWriter eventWriter: xmlEventWriters) {
                          //  eventWriter.add(new CharacterEvent("/n/t/t"));
                            for(XMLEvent xmlEvent: commonPart2EventList ){
                                eventWriter.add(xmlEvent);

                            }
                            eventWriter.close();
                        }
                        writeToFiles();
                    } else {
                        commonPart2EventList.add(event);
                    }

                }
                else {
                    if((outputStreamsForWriters.get(i-1).toByteArray().length+5600)>limitSize){    // 5600 = maxOfferSize + maxCloseInf
                        if(event.isEndElement() && event.asEndElement().getName().getLocalPart().equals("offer")){
                            xmlEventWriters.get(i-1).add(event);
                            System.out.println("outputStream length is "+ outputStreamsForWriters.get(i-1).toByteArray().length);

                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                            initWriter(outputStream);
                        }else{
                            xmlEventWriters.get(i-1).add(event);
                           // System.out.println("event is added");


                        }
                    }else{
                        xmlEventWriters.get(i-1).add(event);
                       // System.out.println("event is added");

                    }
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private XMLEventWriter initWriter(ByteArrayOutputStream outputStream) throws  XMLStreamException {

        XMLEventWriter xmlEventWriter = null;
        try {
            Writer writer = new OutputStreamWriter(outputStream, encoding);
            XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
            xmlEventWriter = oFactory.createXMLEventWriter(writer);
            outputStreamsForWriters.add(outputStream);
            xmlEventWriters.add(xmlEventWriter);
            for(XMLEvent xmlEvent: commonPart1EventList){
                xmlEventWriter.add(xmlEvent);
            }
            i++;
            System.out.println("xmlWriter"+i +"has been created");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error of the writers creating ");
        }
        return xmlEventWriter;
    }

    private void writeToFiles() throws IOException {
        for(int k = 0; k < i; k++){
            OutputStream fou = new FileOutputStream(outputDir+"/output"+k+".xml");
           outputStreamsForWriters.get(k).writeTo(fou);
            System.out.println(outputStreamsForWriters.get(k).toByteArray().length);
            fou.close();
        }
    }


}
