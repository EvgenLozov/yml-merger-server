package com.merger;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Util {

    private static final String DTD_DECLARATION = "<!DOCTYPE yml_catalog SYSTEM \"shops.dtd\">\n";

    public static void marshal(Object t, OutputStream  outputStream, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", DTD_DECLARATION);

        marshaller.marshal( t, outputStream );
    }

    public static void marshal(Object t, String  fileName, String encoding) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance( t.getClass() );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
        marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
        marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders", DTD_DECLARATION);

        marshaller.marshal( t, new FileOutputStream(fileName) );
    }

    public static <T> T unmarshal(InputStream inputStream, Class<T> tClas, String encoding) throws JAXBException, SAXException, ParserConfigurationException, FileNotFoundException, UnsupportedEncodingException {

        JAXBContext jc = JAXBContext.newInstance(tClas);

        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        spf.setFeature("http://xml.org/sax/features/validation", false);

        Reader reader = new InputStreamReader(inputStream, encoding);

        XMLReader xmlReader = spf.newSAXParser().getXMLReader();
        InputSource inputSource = new InputSource(reader);

        SAXSource source = new SAXSource(xmlReader, inputSource);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (T) unmarshaller.unmarshal(source);
    }

    public static <T> T unmarshal(String fileName, Class<T> tClas, String encoding ) throws FileNotFoundException, UnsupportedEncodingException, SAXException, ParserConfigurationException, JAXBException {
        return unmarshal(new FileInputStream(fileName), tClas, encoding);
    }

    public static Document parseDocument(InputStream inputStream, String encoding) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/validation", false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new InputSource(inputStream));
    }


    public static void writeDocument(Document document, OutputStream outputStream, String encoding) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
        Result output = new StreamResult(outputStream);
        Source input = new DOMSource(document);
        transformer.transform(input, output);

    }
}
