package com.company.processing;

import com.company.allowedcategories.IncludedCategoriesProvider;
import com.company.config.Config;
import com.company.factories.handler.*;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import company.Factory;
import company.StAXService;
import company.XMLEventReaderProvider;
import company.bytearray.ByteArraySource;
import company.handlers.xml.XmlEventHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.*;

public class MergedYmlSource implements ByteArraySource {

    Config config;

    public MergedYmlSource(Config config) {
        this.config = config;
    }

    @Override
    public byte[] provide() throws XMLStreamException, FileNotFoundException {


        List<XMLEventReaderProvider> readerProviders = new ArrayList<>();

        if (!config.getUrls().isEmpty()) {
            String psw = new String(Base64.getDecoder().decode(config.getPsw().getBytes()));
            CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), psw).get();
            HttpService httpService = new HttpService(httpClient);

            for (String url : config.getUrls())
                readerProviders.add(new HttpXMLEventReaderProvider(httpService, url, config.getEncoding()));
        }

        for (String fileName : config.getFiles())
            readerProviders.add(new FileXMLEventReaderProvider(fileName, config.getEncoding()));

        if (readerProviders.isEmpty())
            throw new RuntimeException("Must be specified at least one price list source");

        StAXService staxService = new StAXService(readerProviders.get(0));

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();


        ByteOutputStream outputStream  = new ByteOutputStream();
        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(outputStream, config.getEncoding());

        List<Factory<XmlEventHandler>> factories = new ArrayList<>();
        factories.add(new ConditionalCopierXmlEventHandlerFactory(mergedOut, Arrays.asList("categories", "offers")));

        Set<String> allowedCategories = new IncludedCategoriesProvider(readerProviders, new TreeSet<>(config.getCategoryIds())).get();
        factories.add(new ElementWriterHandlerFactory(readerProviders, "offers", new OfferHandlerFactory( mergedOut, allowedCategories)));
        factories.add(new ElementWriterHandlerFactory(readerProviders, "categories", new CategoryHandlerFactory(mergedOut, allowedCategories)));

        XmlEventHandler xmlEventHandler = new MergeXmlEventHandlerFactory(factories).get();

        staxService.process(xmlEventHandler);

        mergedOut.flush();
        mergedOut.close();
        outputStream.close();

        return outputStream.toByteArray();
    }
}
