package com.merger.main;

import com.merger.Util;
import com.merger.http.HttpResponseHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by lozov on 23.06.15.
 */
public class FileDownloadAsDOMHandler implements HttpResponseHandler<Document> {

    private String encoding;

    public FileDownloadAsDOMHandler(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public Document handle(CloseableHttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();

        if (entity == null)
            return null;

        try {
            return Util.parseDocument(entity.getContent(), encoding);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
