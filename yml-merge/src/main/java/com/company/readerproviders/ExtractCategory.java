package com.company.readerproviders;

import com.company.allowedcategories.CategoriesCollectorV2;
import com.company.allowedcategories.Category;
import com.company.http.HttpResponseHandler;
import com.company.logger.ProcessLogger;
import company.StAXService;
import company.handlers.xml.AggregatedXmlEventNotifier;
import org.apache.http.client.methods.CloseableHttpResponse;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ExtractCategory implements HttpResponseHandler<Set<Category>> {

    String encoding;

    public ExtractCategory(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public Set<Category> handle(CloseableHttpResponse httpResponse) {
        XMLInputFactory ifactory = XMLInputFactory.newFactory();
        ifactory.setProperty(XMLInputFactory.IS_VALIDATING, false);
        try {
            XMLEventReader xmlEventReader = ifactory.createXMLEventReader(httpResponse.getEntity().getContent(), encoding);

            Set<Category> allCategories = new HashSet<>();
            AggregatedXmlEventNotifier aggregatedXmlEventNotifier = new AggregatedXmlEventNotifier(new CategoriesCollectorV2(allCategories), "category");
            StAXService stAXService = new StAXService(() -> xmlEventReader );
            stAXService.process(aggregatedXmlEventNotifier, event -> event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("offers"));

            return allCategories;
        } catch (XMLStreamException |  IOException e) {
            String responce = httpResponse.getStatusLine().getStatusCode() + ": " + httpResponse.getStatusLine().getReasonPhrase();
            ProcessLogger.INSTANCE.warning("Unable to process http response\n ; " + e.getMessage() + "; " + responce);
            throw new RuntimeException("Unable to process http response", e);
        }
    }
}
