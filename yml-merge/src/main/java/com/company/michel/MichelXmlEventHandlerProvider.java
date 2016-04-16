package com.company.michel;

import com.company.allowedcategories.AllCategoriesProvider;
import com.company.allowedcategories.IncludedCategoriesProvider;
import com.company.config.MergerConfig;
import com.company.factories.handler.*;
import company.Factory;
import company.handlers.xml.XmlEventHandler;
import company.providers.XMLEventReaderProvider;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.*;

/**
 * @author Yevhen
 */
public class MichelXmlEventHandlerProvider {

    MergerConfig config;
    List<? extends XMLEventReaderProvider> readerProviders;

    public MichelXmlEventHandlerProvider(MergerConfig config, List<? extends XMLEventReaderProvider> readerProviders) {
        this.config = config;
        this.readerProviders = readerProviders;
    }

    public XmlEventHandler get() throws FileNotFoundException, XMLStreamException {
        XMLOutputFactory oFactory = XMLOutputFactory.newFactory();

        File file = new File(config.getOutputFile() + File.separator + "RUR.xml");
        file.getParentFile().mkdirs();

        XMLEventWriter mergedOut = oFactory.createXMLEventWriter(new FileOutputStream(file), config.getEncoding());

        List<Factory<XmlEventHandler>> factories = new ArrayList<>();
        factories.add(new ConditionalCopierXmlEventHandlerFactory(mergedOut, Arrays.asList("categories", "offers")));

        AllCategoriesProvider allCategoriesProvider = new AllCategoriesProvider(config);
        Set<String> allowedCategories = new IncludedCategoriesProvider(allCategoriesProvider, new TreeSet<>()).get();
        factories.add(new ElementWriterHandlerFactory(readerProviders, "offers", new OfferHandlerFactory( mergedOut, allowedCategories, new HashSet<String>())));
        factories.add(new ElementWriterHandlerFactory(readerProviders, "categories", new CategoryHandlerFactory(mergedOut, allowedCategories)));

        return new MergeXmlEventHandlerFactory(factories).get();
    }

}
