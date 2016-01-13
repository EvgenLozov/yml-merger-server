package com.company.repository;

import com.company.allowedcategories.Category;
import com.company.config.CategoryIdsPair;
import com.company.config.MergerConfig;
import com.company.logger.ProcessLogger;

import javax.xml.stream.XMLStreamException;
import java.util.*;

/**
 * @author Yevhen
 */
public class CategoryRepository {

    private static final ProcessLogger logger = ProcessLogger.INSTANCE;

    private Map<String, Set<Category>> categoriesCache = new HashMap<>();
    private Map<String, Date> categoriesCacheDates = new HashMap<>();

    private CategorySource categorySource;

    public CategoryRepository(CategorySource categorySource) {
        this.categorySource = categorySource;
    }

    public Set<Category> children(String configId, String parentId) throws XMLStreamException {
        Set<Category> allCategories = categoriesCache.get(configId);

        Set<Category> children = new HashSet<>();

        if (allCategories == null)
            return children;

        for (Category category : allCategories) {
            if (category.getParentId() != null && category.getParentId().equals(parentId))
                children.add(category);
        }

        return children;
    }

    public void addOrUpdateCache(MergerConfig config) throws XMLStreamException {
        logger.info("Updating categories cache for config : " + config.getName() + " is started.");

        Set<Category> categories = categorySource.get(config.getId());

        for (CategoryIdsPair pair : config.getParentIds()) {
            categories.stream().filter(category -> category.getId().equals(pair.getCategoryId()))
                               .forEach(category -> category.setParentId(pair.getParentId()));
        }
        categoriesCache.put(config.getId(), categories);

        logger.info("Categories cache for config \"" + config.getName() + "\" was updated");

        categoriesCacheDates.put(config.getId(), new Date());
    }

    public Date getCacheDate(String configId){
        return categoriesCacheDates.get(configId);
    }

    public Category setParent(MergerConfig config, String categoryId, String parentId) {
        Set<Category> categories = categoriesCache.get(config.getId());

        for (Category category : categories) {
                if (category.getId().equals(categoryId)){
                    category.setParentId(parentId);
                    return category;
                }
        }

        return null;
    }
}
