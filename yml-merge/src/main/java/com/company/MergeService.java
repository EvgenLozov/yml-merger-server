package com.company;

import com.company.allowedcategories.IncludedCategoriesProvider;
import com.company.config.Config;
import com.company.config.ConfigProvider;
import com.company.factories.handler.*;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import company.Factory;
import company.StAXService;
import company.XMLEventReaderProvider;
import company.handlers.xml.XmlEventHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by user50 on 28.07.2015.
 */
public class MergeService {

    public void process(Config config) throws FileNotFoundException, XMLStreamException {
        CloseableHttpClient httpClient = new HttpClientProvider(config.getUser(), config.getPsw()).get();
        HttpService httpService = new HttpService(httpClient);

        List<XMLEventReaderProvider> readerProviders = new ArrayList<>();

        for (String url : config.getUrls())
            readerProviders.add(new HttpXMLEventReaderProvider(httpService, url, config.getEncoding()));

        for (String fileName : config.getFiles())
            readerProviders.add(new FileXMLEventReaderProvider(fileName, config.getEncoding()));

        StAXService staxService = new StAXService(readerProviders.get(0));

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();
        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(new FileOutputStream(config.getOutputFile()), config.getEncoding());

        List<Factory<XmlEventHandler>> factories = new ArrayList<>();
        factories.add(new CurrencyHandlerFactory(config.getCurrency()));
        factories.add(new ConditionalCopierXmlEventHandlerFactory(mergedOut, Arrays.asList("categories", "offers")));

        Set<String> allowedCategories = new IncludedCategoriesProvider(readerProviders, new TreeSet<>(config.getCategoryIds())).get();
        factories.add(new ElementWriterHandlerFactory(readerProviders, "offers", new OfferHandlerFactory(config.getCurrency(), mergedOut, allowedCategories, config.getReplaces())));
        factories.add(new ElementWriterHandlerFactory(readerProviders, "categories", new CategoryHandlerFactory(mergedOut, allowedCategories)));

        XmlEventHandler xmlEventHandler = new MergeXmlEventHandlerFactory(factories).get();

        staxService.process(xmlEventHandler);

        mergedOut.flush();
        mergedOut.close();
    }

}
