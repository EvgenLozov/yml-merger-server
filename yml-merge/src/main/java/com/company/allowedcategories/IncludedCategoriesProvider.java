package com.company.allowedcategories;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import company.StAXService;
import company.XMLEventReaderProvider;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user50 on 11.07.2015.
 */
public class IncludedCategoriesProvider {

    private List<XMLEventReaderProvider> readerProviders;
    private Set<String> categoryIds;

    public IncludedCategoriesProvider(List<XMLEventReaderProvider> readerProviders, Set<String> categoryIds) {
        this.readerProviders = readerProviders;
        this.categoryIds = categoryIds;
    }

    public Set<String> get() throws FileNotFoundException, XMLStreamException {
        Set<Category> allCategories = new HashSet<>();
        CategoriesCollector categoriesCollector = new CategoriesCollector(allCategories);
        for (XMLEventReaderProvider readerProvider : readerProviders) {
            StAXService stAXService = new StAXService(readerProvider);
            stAXService.process(categoriesCollector);
        }

        Set<Category> categoriesFromConfig = new HashSet<>();
        for (Category category : allCategories) {
            if (categoryIds.isEmpty() || categoryIds.contains(category.getId()))
                categoriesFromConfig.add(category);
        }

        ListMultimap<String, Category> multimap = ArrayListMultimap.create();

        for (Category category : allCategories) {
            multimap.get(category.getParentId()).add(category);
        }

        Set<Category> categoriesToInclude = new Util(multimap).getDescendants(categoriesFromConfig);
        Set<String> categoryIdsToInclude = new HashSet<>();
        for (Category category : categoriesToInclude) {
            categoryIdsToInclude.add(category.getId());
        }

        return categoryIdsToInclude;
    }
}
