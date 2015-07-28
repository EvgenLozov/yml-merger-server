package com.company.readerproviders;

import com.company.http.HttpResponseHandler;
import com.company.readerproviders.exception.IOResponseHandleException;
import com.company.readerproviders.exception.XmlProcessingException;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

/**
 * Created by user50 on 04.07.2015.
 */
public class StaxResponseHandler implements HttpResponseHandler<XMLEventReader> {

    String encoding;

    public StaxResponseHandler(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public XMLEventReader handle(CloseableHttpResponse httpResponse){
        XMLInputFactory ifactory = XMLInputFactory.newFactory();

        try {
            return ifactory.createXMLEventReader(httpResponse.getEntity().getContent(), encoding);
        } catch (XMLStreamException e) {
            throw new XmlProcessingException("Unable to process http response!", e);
        } catch (IOException e) {
            throw new IOResponseHandleException("I/O exception!", e);
        }
    }
}
