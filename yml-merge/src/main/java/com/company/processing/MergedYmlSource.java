package com.company.processing;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.IncludedCategoriesProvider;
import com.company.config.Config;
import com.company.factories.handler.*;
import com.company.http.HttpClientProvider;
import com.company.http.HttpService;
import com.company.readerproviders.FileXMLEventReaderProvider;
import com.company.readerproviders.HttpXMLEventReaderProvider;
import company.Factory;
import company.StAXService;
import company.XMLEventReaderProvider;
import company.bytearray.ByteArraySource;
import company.handlers.xml.XmlEventHandler;
import org.apache.http.impl.client.CloseableHttpClient;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class MergedYmlSource implements ByteArraySource {

    Config config;
    List<XMLEventReaderProvider> readerProviders;

    public MergedYmlSource(Config config, List<XMLEventReaderProvider> readerProviders) {
        this.config = config;
        this.readerProviders = readerProviders;
    }

    @Override
    public byte[] provide() throws XMLStreamException, IOException {
        if (readerProviders.isEmpty())
            throw new RuntimeException("Must be specified at least one price list source");

        StAXService staxService = new StAXService(readerProviders.get(0));

        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();


        ByteArrayOutputStream outputStream  = new ByteArrayOutputStream();
        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(outputStream, config.getEncoding());

        List<Factory<XmlEventHandler>> factories = new ArrayList<>();
        factories.add(new ConditionalCopierXmlEventHandlerFactory(mergedOut, Arrays.asList("categories", "offers")));

        AllCategoriesProvider allCategoriesProvider = new AllCategoriesProvider(readerProviders);
        Set<String> allowedCategories = new IncludedCategoriesProvider(allCategoriesProvider, new TreeSet<>(config.getCategoryIds())).get();
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
