package com.company.allowedcategories;

import company.StAXService;
import company.XMLEventReaderProvider;
import company.handlers.xml.AggregatedXmlEventNotifier;

import javax.xml.stream.XMLStreamException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yevhen
 */
public class AllCategoriesProvider {

    private List<XMLEventReaderProvider> readerProviders;

    public AllCategoriesProvider(List<XMLEventReaderProvider> readerProviders) {
        this.readerProviders = readerProviders;
    }

    public Set<Category> get() throws XMLStreamException {
        Set<Category> allCategories = new HashSet<>();
        AggregatedXmlEventNotifier aggregatedXmlEventNotifier = new AggregatedXmlEventNotifier(new CategoriesCollectorV2(allCategories), "category");
        for (XMLEventReaderProvider readerProvider : readerProviders) {
            StAXService stAXService = new StAXService(readerProvider);
            stAXService.process(aggregatedXmlEventNotifier);
        }

        return allCategories;
    }
}
